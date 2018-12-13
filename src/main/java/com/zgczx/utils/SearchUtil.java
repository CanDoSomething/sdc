package com.zgczx.utils;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Auther: 陈志恒
 * @Date: 2018/12/11 00:14
 * @Description: 查找中间类
 */
@Data
@ToString
public class SearchUtil {
    /*查找字段*/
    private List<String> keywords;
//    private String keywords;
    /*排序字段*/
    private String orderBy;
    /*默认查找排序方式为升序*/
    private Sort.Direction orderDirection = Sort.Direction.ASC;
    /*默认开始的页数*/
    private int page = 0;
    /*默认的每页的大小为10*/
    private int size = 10;

    /*如果用户输入的页数小于0那么让页数等于0*/
    public int getPage() {
        return page > 0 ? page : 0;
    }
    public void setPage(int page) {
        this.page = page;
    }
    /*同理为每页显示的数量做出处理*/
    public int getSize() {
        if (this.size < 1) {
            return 10;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

}
