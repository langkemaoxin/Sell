package com.rex.sell;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class SellApplicationTests1 {

    @Test
    void contextLoads() {
        log.debug("debug....");
        log.info("info....");
        log.error("error....");

        //带参数用法
        log.error("name:{},password={}","rex","123456");

    }

}
