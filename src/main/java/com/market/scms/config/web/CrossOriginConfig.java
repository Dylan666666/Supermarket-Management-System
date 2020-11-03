package com.market.scms.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 21:21
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .allowedHeaders("*")
                //cookie设置
                .allowCredentials(true).maxAge(3600);
    }
}
