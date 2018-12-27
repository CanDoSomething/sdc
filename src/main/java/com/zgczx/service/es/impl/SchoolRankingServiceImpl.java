package com.zgczx.service.es.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgczx.dataobject.es.SchoolRanking;
import com.zgczx.repository.es.SchoolRankingRepository;
import com.zgczx.service.es.ISchoolRankingService;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/3 16:21
 * @Description:es查询文件
 */
@Service
public class SchoolRankingServiceImpl implements ISchoolRankingService {
    @Autowired
    private SchoolRankingRepository schoolRankingRepository;
//    @Autowired
//    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestHighLevelClient client;
    @Override
    public void index(Integer schoolid) {
        SchoolRanking schoolRanking = schoolRankingRepository.findOne(schoolid);
        if (schoolRanking==null){
            return;
        }
        IndexRequest request = new IndexRequest(
                "school",
                "school_ranking",
                schoolid.toString());
        try {
            request.source(objectMapper.writeValueAsBytes(schoolRanking), XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse.status());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer schoolid) {

    }

    @Override
    public void find(Integer docid) {
        GetRequest request = new GetRequest("school", "school_ranking", docid.toString());
        try {
            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            System.out.println(sourceAsMap.get("schoolid"));
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void query(String keywords) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery= QueryBuilders.boolQuery();
        boolQuery.must(
                QueryBuilders.multiMatchQuery(keywords,
                        "schoolname",
                        "province",
                        "schooltype",
                        "schoolproperty",
                        "level",
                        "schoolnature"
                ));
        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse=client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                System.out.println(sourceAsMap.get("schoolname"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
