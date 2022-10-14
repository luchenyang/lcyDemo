package com.lcy.demo.springcloud.openfegin;
import feign.hystrix.FallbackFactory;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/10/14
 */
public class TestFallBackFactory implements FallbackFactory<TestFegin> {

    @Override
    public TestFegin create(Throwable cause) {
        return null;
    }
}