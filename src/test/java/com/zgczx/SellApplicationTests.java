package com.zgczx;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j //工具类
public class SellApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1(){
		String name = "immoc";
		String password = "123456";
		log.info("name: "+name+"password"+password);
		log.info("name: {},password: {}",name,password); //使用方便
		log.debug("debug...");
		log.error("error...");
	}
}
