package com.zgczx.repository;

import com.zgczx.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    private final String OPENID = "110110";

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("中关村");
        orderMaster.setOrderAmount(new BigDecimal(2.3));
        orderMaster.setBuyerOpenid("110110");

        OrderMaster orderMaster1 =  repository.save(orderMaster);
        Assert.assertNotNull(orderMaster); //不为空
    }

    @Test
    public void findByBuAndBuyerOpenid() throws Exception {
        PageRequest  request = new PageRequest(0,1);

        Page<OrderMaster> result =  repository.findByBuyerOpenid(OPENID,request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

}