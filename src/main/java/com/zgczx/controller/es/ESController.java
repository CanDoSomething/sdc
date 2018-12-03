package com.zgczx.controller.es;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.zgczx.VO.ResultVO;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/3 09:36
 * @Description:es
 */
@RestController
public class ESController {
    @Autowired
    private RestClient client;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @GetMapping(value = "/es/{id}")
    public ResultVO<String> getBookById(@PathVariable("id") String id) {
        Request request = new Request("GET", new StringBuilder("/xunwu/house/").
                append(id).toString());
        // 添加json返回优化
        request.addParameter("pretty", "true");
        Response response = null;
        String responseBody = null;
        try {
            // 执行HHTP请求
            response = client.performRequest(request);
            System.out.println(response);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            return new ResultVO<String>("can not found the book by your id", HttpStatus.NOT_FOUND);
        }
        return new ResultVO<String>(responseBody, HttpStatus.OK);
    }
    @GetMapping(value = "es/getById/{id}")
    public ResultVO<List<String>> getBookByCity(@PathVariable("id") String id) {
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.filter(
                QueryBuilders.termQuery("cityEnName","bj")
        );
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("xunwu");
        searchRequest.types("house");
        searchRequest.source(sourceBuilder);
        SearchResponse response = null;
        List<String> result=new ArrayList<>();
        try {
            response = restHighLevelClient.search(searchRequest);
            SearchHits hits= response.getHits();
            int totalRecordNum= (int) hits.getTotalHits();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                    .create();
            for (SearchHit searchHit : hits) {
                Map<String, Object> source = searchHit.getSourceAsMap();
                result.add(gson.toJson(source));
                System.out.println(gson.toJson(source));
            }
            restHighLevelClient.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResultVO<List<String>>(result,HttpStatus.OK);
    }

}
