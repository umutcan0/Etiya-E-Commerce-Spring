package com.etiya.ecommerce.core.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfigurations {
    @Bean
    public ModelMapper getMap() { return new ModelMapper();}

}
