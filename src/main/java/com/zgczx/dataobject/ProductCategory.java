package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 类目表
 */
@Entity
@DynamicUpdate //动态更新的目的，比如时间自动更新
@Data //省略get，set
public class ProductCategory {
    //类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //类目名字
    private String categoryName;

    //类目编号
    private Integer categoryType;

    public ProductCategory(String categoryName,Integer categoryType){
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory(){

    }
}
