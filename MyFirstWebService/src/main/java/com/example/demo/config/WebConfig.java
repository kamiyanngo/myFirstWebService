package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

       registry.addResourceHandler("/img/**")
          .addResourceLocations("file:///F:/programing/MyFirstWebService/MyFirstWebService/static/img/");
    }
    
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
} 