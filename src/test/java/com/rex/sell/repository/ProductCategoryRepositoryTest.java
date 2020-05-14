package com.rex.sell.repository;

import com.rex.sell.dataobject.ProductCategory;
import org.hibernate.criterion.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    void findoneTest() {

        Optional<ProductCategory> result = repository.findById(1);
        ProductCategory item = result.isPresent() ? result.get() : null;
        System.out.println(item);
    }

    @Test
    void insertTest() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("女生最爱1");
        productCategory.setCategoryType(3);
        repository.save(productCategory);

    }


    @Test
    @Transactional //测试完就回滚
    void insertTest1() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("XXXXXXXXXXX");
        productCategory.setCategoryType(5);
        ProductCategory save = repository.save(productCategory);
        Assert.notNull(save,"ok");

    }

    @Test
    void updateTest() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(3);
        productCategory.setCategoryName("女生最爱333");
        productCategory.setCategoryType(4);
        ProductCategory save = repository.save(productCategory);
        Assert.notNull(save,"更新成功");
    }

    @Test
    void findByCategoryTypeIn(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<ProductCategory> list = repository.findByCategoryTypeIn(integers);
//        System.out.println(list.size());
        for (ProductCategory item: list) {
            System.out.println(item);
        }
    }
}