package com.personal.blog_app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private static ModelMapper modelMapper;

    @Bean
    public static ModelMapper modelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
        }
        return new ModelMapper();
    }

    public static ModelMapper getMapper() {
        return modelMapper;
    }
}
