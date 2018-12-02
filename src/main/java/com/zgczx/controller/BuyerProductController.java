package com.zgczx.controller;

import com.zgczx.VO.ProductInfoVO;
import com.zgczx.VO.ProductVO;
import com.zgczx.VO.ResultVO;
import com.zgczx.dataobject.ProductCategory;
import com.zgczx.dataobject.ProductInfo;
import com.zgczx.service.CategoryService;
import com.zgczx.service.ProductService;
import com.zgczx.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        System.out.println(productInfoList.size());

        //2.查询类目（一次性查询）

//        传统方法
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo: productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }

        //精简方法（jdk8.0 lambda表达式）
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) { //遍历所有商品，如果属于这一类型，则加入进来
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);

        }

        return ResultVOUtil.success(productVOList);
    }

}
