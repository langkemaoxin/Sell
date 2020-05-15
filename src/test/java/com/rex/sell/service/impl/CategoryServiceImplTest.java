package com.rex.sell.service.impl;

import com.rex.sell.dataobject.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory one = categoryService.findOne(1);
        Assert.isTrue(one.getCategoryId()==1,"成功");
    }

    @Test
    void findAll() {
        List<ProductCategory> all = categoryService.findAll();
        Assert.isTrue(all.size()!=0,"成功");
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4, 5));
        Assert.isTrue(byCategoryTypeIn.size()!=0,"");
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory("AAA", 10);
        ProductCategory save = categoryService.save(productCategory);
        Assert.notNull(save, "");

    }
}