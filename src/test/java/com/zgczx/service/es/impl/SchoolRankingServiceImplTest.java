package com.zgczx.service.es.impl;

import com.zgczx.SellApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SchoolRankingServiceImplTest extends SellApplicationTests {
    @Autowired
    private SchoolRankingServiceImpl schoolRankingService;
    @Test
    public void index() {
        for (int i=30;i<2795;i++){
            schoolRankingService.index(new Integer(i));
        }
//        schoolRankingService.index(new Integer(30));
    }
    @Test
    public void find(){
        schoolRankingService.find(new Integer(1));
    }
    @Test
    public void query(){
        String keywords="老子要去上海的综合的大学";
        schoolRankingService.query(keywords);
    }
}