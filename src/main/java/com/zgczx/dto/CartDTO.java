package com.zgczx.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CartDTO {
    //商品
    private String productId;
    //数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
