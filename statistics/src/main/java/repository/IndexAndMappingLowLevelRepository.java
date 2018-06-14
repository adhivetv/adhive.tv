package repository;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class IndexAndMappingLowLevelRepository {

    private RestClient restClient;

    public IndexAndMappingLowLevelRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    public Response createIndex(String index) throws IOException {
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        Response response = this.restClient.performRequest("PUT", "/" + index, params, new Header[0]);
        return response;
    }

    public Response deleteIndex(String index) throws IOException {
        Map<String, String> params = Collections.emptyMap();
        Response response = this.restClient.performRequest("DELETE", "/" + index, params, new Header[0]);
        return response;
    }

    public Response putMapping(String index, String type, String mappingSource) throws IOException {
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        HttpEntity entity = new NStringEntity(mappingSource, ContentType.APPLICATION_JSON);
        Response response = this.restClient.performRequest("PUT", "/" + index + "/_mapping/" + type, params, entity, new Header[0]);
        return response;
    }

    public Response getMapping(String index, String type) throws IOException {
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        Response response = this.restClient.performRequest("GET", "/" + index + "/_mapping/" + type, params, new Header[0]);
        return response;
    }
}
