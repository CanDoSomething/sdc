package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    //详情id
    @Id
    private String detailId;
    //订单id
    private String orderId;
    //产品id
    private String productId;
    //商品名称
    private String productName;
    //单价
    private BigDecimal productPrice;
    //数量
    private Integer productQuantity;
    //小图
    private String productIcon;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}
