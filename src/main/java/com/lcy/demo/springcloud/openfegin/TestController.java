package com.lcy.demo.springcloud.openfegin;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestApi {


    @Override
    public String getsto(){
        return "ssss";
    }

    @GetMapping("/aaa")
    // 熔断 服务降级
    @HystrixCommand
    public void aaa(){

    }

//    @Autowired
//    private RestTemple restTemple;
//
//    @GetMapping("xxx")
//    String xxx(){
//        return  restTemple.getSomeThing();
//    }

}
