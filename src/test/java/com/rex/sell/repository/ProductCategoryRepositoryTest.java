package com.rex.sell.repository;

import com.rex.sell.dataobject.ProductCategory;
import org.hibernate.criterion.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    void findoneTest() {
//        ProductCategory productCategory=new ProductCategory();
//        productCategory.setCategoryId(1);
//        Example example = Example.of()
//        Optional<ProductCategory> one = repository.findOne(example);

        Optional<ProductCategory> result = repository.findById(1);
        ProductCategory item = result.isPresent() ? result.get() : null;
        System.out.println(item);
    }
}