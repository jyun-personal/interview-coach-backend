package com.interviewcoach.config;

import com.interviewcoach.dto.ProfileResponseDto;
import com.interviewcoach.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        
        // Configure mapping from Profile to ProfileResponseDto to skip the ID field
        // since Profile.id is UUID but ProfileResponseDto.id should be User's Long ID
        mapper.createTypeMap(Profile.class, ProfileResponseDto.class)
            .addMappings(mapping -> {
                mapping.skip(ProfileResponseDto::setId); // Skip automatic ID mapping
            });
            
        return mapper;
    }
}
