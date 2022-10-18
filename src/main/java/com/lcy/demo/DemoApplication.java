package com.lcy.demo;

import com.lcy.demo.bean.register.TestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableHystrix
@EnableFeignClients(basePackages = {"com.lcy"})
@TestBean
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication springBootApplication = new SpringApplication(DemoApplication.class);
//        springBootApplication.setWebApplicationType(WebApplicationType.NONE);
        springBootApplication.run(args);

    }

}
