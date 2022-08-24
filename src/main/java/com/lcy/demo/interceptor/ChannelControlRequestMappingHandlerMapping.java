package com.lcy.demo.interceptor;


import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class ChannelControlRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        RequestMappingInfo build = null;
        if (info != null) {
            String packageName = handlerType.getPackage().getName();
            int legth = packageName.indexOf("controller.");
            if(legth>=0){
                build = RequestMappingInfo.paths("/" + packageName.substring(legth + 11, legth + 13)).build();
                info = build.combine(info);
            }
        }
        return info;
    }


}