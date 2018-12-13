package com.zgczx.utils.tea;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by Dqd on 2018/12/12.
 * 分页工具类
 *
 */
public class SplitPageUtils {
    public static Pageable getPageable(int page,int pageSize,String sortField){
        Sort sort = new Sort(Sort.Direction.DESC, sortField);
        Pageable pageable = new PageRequest(page,pageSize);
        return pageable;
    }
}
