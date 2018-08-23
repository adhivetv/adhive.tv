package geolocation.repository;

import geolocation.model.Locality;
import geolocation.service.LocalityImportService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.JsonMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Repository
public class LocalityRepository {

    private static final Logger log = LoggerFactory.getLogger(LocalityRepository.class);

    private RestHighLevelClient client;

    private LocalityImportService localityImportService;

    public LocalityRepository() {
    }

    @Autowired
    public LocalityRepository(RestHighLevelClient client, LocalityImportService localityImportService) {
        this.client = client;
        this.localityImportService = localityImportService;
    }

    public int saveLocality(String index, String type, Locality locality) throws IOException {
        log.debug("saveLocality() - start, locality = {}", locality);

        IndexRequest indexRequest = new IndexRequest(index, type, locality.getId());
        final String city = JsonMapper.toJson(locality);
        indexRequest.source(city, XContentType.JSON);
        RestStatus status = client.index(indexRequest).status();

        log.debug("saveLocality() end");
        return status.getStatus();
    }

    private List<Locality> getSearchResult(String index, String type, SearchSourceBuilder sourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        final SearchResponse search = client.search(searchRequest);
        final SearchHit[] hits = search.getHits().getHits();
        return Arrays.stream(hits)
                .map(hit -> JsonMapper.fromJson(hit.getSourceAsString(), Locality.class))
                .collect(Collectors.toList());
    }

    public List<Locality> findAll(String index, String type) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(10000);
        sourceBuilder.query(matchAllQuery());

        return getSearchResult(index, type, sourceBuilder);
    }

    public List<Locality> findByCountry(String index, String type, String country) throws IOException {
        log.debug("findByCountry - start: country = {} ", country);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQuery().must(QueryBuilders.matchPhraseQuery("country", country)));
        sourceBuilder.size(100);

        List<Locality> result = getSearchResult(index, type, sourceBuilder);

        log.debug("findByCountry  - end: country = {} ", country);
        return result;
    }

    public List<Locality> findByName(String index, String type, String name) throws IOException {
        log.debug("findByName - start: name = {} ", name);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQuery().must(QueryBuilders.matchPhraseQuery("name", name)));
        sourceBuilder.size(100);

        List<Locality> result = getSearchResult(index, type, sourceBuilder);

        log.debug("findByName  - end: name = {} ", name);
        return result;
    }
}