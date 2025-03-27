package com.example.plusproject.common.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// dto -> entity 변환 시 null 이 아닌 필드만 복사하도록 도와주는 util
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull()); // null 값은 무시
        return mapper;
    }
}
