package com.rex.sell.controller;

import com.rex.sell.VO.ProductVO;
import com.rex.sell.VO.ProductInfoVO;
import com.rex.sell.VO.ResultVO;
import com.rex.sell.dataobject.ProductCategory;
import com.rex.sell.dataobject.ProductInfo;
import com.rex.sell.service.CategoryService;
import com.rex.sell.service.ProductService;
import com.rex.sell.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BuyerProductController
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/15 22:08
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResultVO<List<ProductVO>> getList(){

        //1. 查询所有的上架商品
        List<ProductInfo> productInfos = productService.findUpAll();

        //2. 查询所有的类目信息
        List<Integer> integers = productInfos.stream().map(o -> o.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(integers);

        List<ProductVO> productVOS=new ArrayList<>();
        //3. 组装数据
        for (ProductCategory productCategory: productCategories) {
            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryId());
            productVO.setCategoryName(productCategory.getCategoryName());

            //商品信息
            List<ProductInfoVO> productInfoVOS=new ArrayList<>();
            for(ProductInfo productInfo :productInfos){
                if(productInfo.getCategoryType()!=productCategory.getCategoryType()){
                   continue;
                }
                ProductInfoVO productInfoVO=new ProductInfoVO();
                BeanUtils.copyProperties(productInfo,productInfoVO);
                productInfoVOS.add(productInfoVO);
            }
            productVO.setProductInfoVOList(productInfoVOS);
            productVOS.add(productVO);
        }


        return ResultVOUtils.success(productVOS);
    }
}
