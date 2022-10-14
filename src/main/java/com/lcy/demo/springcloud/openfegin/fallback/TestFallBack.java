package com.lcy.demo.springcloud.openfegin.fallback;

import com.lcy.demo.springcloud.openfegin.TestApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/10/14
 */
@Log4j2
@Component
public class TestFallBack implements TestApi {

    @Override
    public String getsto() {
        log.info("TestFallBack");
        return "aaa";
    }
}