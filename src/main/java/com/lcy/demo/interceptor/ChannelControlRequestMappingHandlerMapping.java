package com.lcy.demo.interceptor;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ChannelControlRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

//    @Override
//    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
//        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
//        RequestMappingInfo build = null;
//        if (info != null) {
//            String packageName = handlerType.getPackage().getName();
//            int legth = packageName.indexOf("controller.");
//            if(legth>=0){
//                build = RequestMappingInfo.paths("/" + packageName.substring(legth + 11, legth + 13)).build();
//                info = build.combine(info);
//            }
//        }
//        return info;
//    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) || AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class))
        && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
    }


}