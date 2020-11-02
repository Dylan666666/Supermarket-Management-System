package com.market.scms.config.web;

import com.market.scms.intercepter.TokenInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Author: Mr_OO
 * @Date: 2020/9/23 12:09
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    public static final String DEFAULT_PREFIX = "classpath:/templates/";

    public static final String DEFAULT_SUFFIX = ".html";

    /**
     * Spring容器
     */
    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 定义默认的请求处理器
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 创建 viewResolver
     * @return
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置Spring 容器
        viewResolver.setApplicationContext(this.applicationContext);
        // 取消缓存
        viewResolver.setCache(false);
        //访问前缀
        viewResolver.setPrefix(DEFAULT_PREFIX);
        //访问后缀
        viewResolver.setSuffix(DEFAULT_SUFFIX);
        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index");
    }

    /**
     * 添加拦截器配置
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //TODO
        /** 职工部分 **/
        String interceptPath1 = "/staff/**";
        String interceptPath2 = "/supplier/**";
        String interceptPath3 = "/purchase/**";
        String interceptPath4 = "/sale/**";
        String interceptPath5 = "/refund/**";
        String interceptPath6 = "/stocktaking/**";
        // 注册拦截器
        InterceptorRegistration loginIR = registry.addInterceptor(new TokenInterceptor());
        // 配置拦截的路径
        loginIR.addPathPatterns(interceptPath1);
        loginIR.addPathPatterns(interceptPath2);
        loginIR.addPathPatterns(interceptPath3);
        loginIR.addPathPatterns(interceptPath4);
        loginIR.addPathPatterns(interceptPath5);
        loginIR.addPathPatterns(interceptPath6);
        /** 登录请求解除拦截 **/
        loginIR.excludePathPatterns("/staff/login");
        loginIR.excludePathPatterns("/supplier/login");
        /** 注册请求解除拦截 **/
        loginIR.excludePathPatterns("/staff/insert");
        loginIR.excludePathPatterns("/supplier/insert");
    }
    
}

