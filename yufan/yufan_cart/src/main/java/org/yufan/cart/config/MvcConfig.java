package org.yufan.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yufan.cart.interceptor.LoginInterceptor;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor interceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( interceptor() ).addPathPatterns("/cart/**");
    }
}
