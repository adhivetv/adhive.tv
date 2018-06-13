package component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import repository.IndexAndMappingLowLevelRepository;

import java.io.IOException;

@Component
public class IndexAndMapping {

    private static final Logger log = LoggerFactory.getLogger(IndexAndMapping.class);

    @Value("${elasticsearch.mapping.types}")
    private String[] types;

    @Value("${elasticsearch.mapping.path}")
    private String path;

    private ResourceLoader resourceLoader;

    private IndexAndMappingLowLevelRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public IndexAndMapping(ResourceLoader resourceLoader, IndexAndMappingLowLevelRepository repository) {
        this.resourceLoader = resourceLoader;
        this.repository = repository;
    }

    public void checkAndUpdate() throws IOException {
        for (String type : types) {
            String index = type + "-index";

            String mappingSource = IOUtils.toString(resourceLoader.getResource(path + type + ".json").getInputStream(), "UTF-8");
            final JsonNode expectedTree = mapper.readTree(mappingSource);
            final JsonNode expectedProperties = expectedTree.get(type);

            Response response = repository.getMapping(index, type);
            final String current = EntityUtils.toString(response.getEntity());
            System.out.println(current);

            final JsonNode currentTree = mapper.readTree(current);
            final JsonNode currentProperties = currentTree.get(index).get("mappings").get(type);

            if (expectedProperties.equals(currentProperties)) {
                log.info("Elasticsearch Index {} is valid", index);
            } else {
                log.info("Elasticsearch Index {} mapping update start", index);
                repository.putMapping(index, type, mapper.writeValueAsString(expectedProperties));
                log.info("Elasticsearch Index {} mapping update finish", index);
            }
        }
    }

    void setTypes(String... types) {
        this.types = types;
    }

    void setPath(String path) {
        this.path = path;
    }
}
