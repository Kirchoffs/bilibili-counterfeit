package com.syh.bilibili.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class JsonHttpMessageConverterConfig {
    @Bean
    @Primary
    public HttpMessageConverter jsonHttpMessageConverter() {
        return null;
    }
}
