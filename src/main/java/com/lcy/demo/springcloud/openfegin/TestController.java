package com.lcy.demo.springcloud.openfegin;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController implements TestFegin {


    /**
     * bb
     * @pReturn {@link List}
     */
    @Override
    public String getsto(){
        return "ssss";
    }

    /**
     * aa
     * @open
     * @rReturn {@link List<test>}
     * @pReturn {@link List}
     */
    @GetMapping("/aaa")
    // 熔断 服务降级
    @HystrixCommand
    public void aaa(){

    }



}
