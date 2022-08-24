package com.lcy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication springBootApplication = new SpringApplication(DemoApplication.class);
//        springBootApplication.setWebApplicationType(WebApplicationType.NONE);
        springBootApplication.run(args);

    }

}
