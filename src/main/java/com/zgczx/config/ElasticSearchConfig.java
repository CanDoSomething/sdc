package com.zgczx.config;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/2 21:24
 * @Description:
 */
@Configuration
public class ElasticSearchConfig {
//    @PostConstruct
//    void init() {
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//    }
    @Bean
    public RestClient getClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        // 添加其他配置，返回来的还是RestClientBuilder对象，这些配置都是可选的
        // clientBuilder.setXX()...

        // 最后配置好的clientBuilder再build一下即可得到真正的Client
        return clientBuilder.build();
    }
    @Bean
    public RestClientBuilder restClient() {
        return RestClient.builder(new HttpHost("localhost", 9200, "http"));
    }
    @Bean
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClient) {
        return new RestHighLevelClient(restClient);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
