package com.rex.sell.repository;

import com.rex.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName ProductCategoryRepository
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/14 22:55
 * @Version 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);
}
