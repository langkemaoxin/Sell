package com.rex.sell.service;

import com.rex.sell.dataobject.Cart;
import com.rex.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName ProductService
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/15 21:08
 * @Version 1.0
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<Cart> carts);

    //减库存
    void decreaseStock(List<Cart> carts);
}
