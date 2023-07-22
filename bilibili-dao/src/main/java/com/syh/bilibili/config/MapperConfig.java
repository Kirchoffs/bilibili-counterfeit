package com.syh.bilibili.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-data.properties")
public class MapperConfig {
    @Value("${mybatis.mapper-locations}")
    private String mybatisMapperLocation;

    @Value("${mybatis.type-aliases-package}")
    private String mybatisTypeAliasesPackage;
}
