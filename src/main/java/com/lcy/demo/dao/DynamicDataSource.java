package com.lcy.demo.dao;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
/**
 * 扩展 Spring 的 AbstractRoutingDataSource 抽象类，重写 determineCurrentLookupKey 方法
 * 动态数据源
 * determineCurrentLookupKey() 方法决定使用哪个数据源
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

        /**
         * 决定使用哪个数据源之前需要把多个数据源的信息以及默认数据源信息配置好
         *
         * @param defaultTargetDataSource 默认数据源
         * @param targetDataSources       目标数据源
         */
        public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
            super.setDefaultTargetDataSource(defaultTargetDataSource);
            super.setTargetDataSources(targetDataSources);
            super.afterPropertiesSet();
        }

        @Override
        protected Object determineCurrentLookupKey() {
            //判断获取那个数据源的方法
            return null;
        }

    }


}
