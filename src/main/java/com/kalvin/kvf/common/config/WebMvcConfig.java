package com.kalvin.kvf.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Kalvin
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/403").setViewName("/403");
        registry.addViewController("/404").setViewName("/404");
    }

    /** 本地文件 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /local/** 指向 /nhga/file/stable/
        registry.addResourceHandler("/qrcode/**")
                .addResourceLocations("file:D:/QRCode/");
    }
}
