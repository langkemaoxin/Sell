package com.rex.sell.repository;

import com.rex.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName ProductInfoRepository
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/15 8:35
 * @Version 1.0
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer status);
}
