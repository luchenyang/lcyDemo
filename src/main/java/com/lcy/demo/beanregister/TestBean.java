package com.lcy.demo.beanregister;

import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/10/14
 */
@Import(TestServerResister.class)
public @interface TestBean {
}
