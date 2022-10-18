package com.lcy.demo.bean.register;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/10/14
 */
public class TestServerResister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TestServer.class);
        beanDefinitionBuilder.setInitMethodName("init");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

//   假如Bean需要定义名称可以使用registry.registerBeanDefinition
//   假如不需要名称，那么使用BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);

//        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
//        registry.registerBeanDefinition("a",beanDefinition);
    }


}