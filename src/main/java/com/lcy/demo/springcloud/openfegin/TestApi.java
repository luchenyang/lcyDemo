package com.lcy.demo.springcloud.openfegin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/10/14
 */
public interface TestApi {

    @GetMapping("/getsomeThing")
    public String getsto();
}