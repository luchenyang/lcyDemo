package com.lcy.demo.dao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DataSourceConfig {


        @Bean
        @Primary
        @ConfigurationProperties(prefix = "ds.financial.master")
        public DataSource financialMasterDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean
        @ConfigurationProperties(prefix = "ds.financial.slave")
        public DataSource financialSlaveDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean(name = "dynamicDataSource")
        public DynamicDataSource dynamicDataSource() {
            Map<Object, Object> dataSourceMap = new HashMap<>();
            dataSourceMap.put("financial-master", financialMasterDataSource());
            dataSourceMap.put("financial-slave", financialSlaveDataSource());
            return new DynamicDataSource(financialMasterDataSource(),dataSourceMap);
        }
}
