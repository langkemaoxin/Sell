package com.rex.sell.service.impl;

import com.rex.sell.dataobject.Cart;
import com.rex.sell.dataobject.ProductInfo;
import com.rex.sell.enums.ProductStatusEnum;
import com.rex.sell.enums.ResultEnum;
import com.rex.sell.exception.SellException;
import com.rex.sell.repository.ProductInfoRepository;
import com.rex.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/15 21:12
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> byId = repository.findById(productId);
        return byId.get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        List<ProductInfo> list = repository.findByProductStatus(ProductStatusEnum.UP.getCode());
        return list;
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<Cart> carts) {
        for (Cart cart:carts){
            ProductInfo productInfo = findOne(cart.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result= productInfo.getProductStock()+cart.getProductQuantity();
            productInfo.setProductStock(result);
            save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<Cart> carts) {
        for (Cart cart:carts){
            ProductInfo productInfo = findOne(cart.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result= productInfo.getProductStock()-cart.getProductQuantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            save(productInfo);
        }
    }
}
