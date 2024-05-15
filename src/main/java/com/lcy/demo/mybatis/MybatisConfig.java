package com.lcy.demo.mybatis;

import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @description:
 * @author: luchenyang
 * @date: 2024/5/15
 */
@Configuration
public class MybatisConfig {

    @Resource(name = "dynamicDataSource")
    private DynamicDataSource dynamicDataSource;

    /**
     *
     * @return
     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.wch.financial.domain");
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    /**
//     * 注册 sqlSessionTemplate
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}