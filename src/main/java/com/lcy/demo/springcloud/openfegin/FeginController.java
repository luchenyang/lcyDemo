package com.lcy.demo.springcloud.openfegin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class FeginController {

    @GetMapping("getsomeThing")
    String getsto(){
        return "ssss";
    }

//    @Autowired
//    private RestTemple restTemple;
//
//    @GetMapping("xxx")
//    String xxx(){
//        return  restTemple.getSomeThing();
//    }

}
