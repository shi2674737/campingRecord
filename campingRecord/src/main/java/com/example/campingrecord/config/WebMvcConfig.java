package com.example.campingrecord.config;

import com.example.campingrecord.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 跨域
 *
 * @author scy
 * @name WebMvcConfig
 * @date 2022-07-21
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS").allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers", "access-control-allow-methods",
                        "access-control-allow-origin", "access-control-max-age", "X-Frame-Options")
                .allowCredentials(true).maxAge(3600);
    }

    @Bean
    public JwtInterceptor requestHandlerInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHandlerInterceptor())
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/user/login");
    }

    @Bean
    public Filter characterEncodingHttpFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
}
