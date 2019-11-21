package com.fc.project3.mycontact.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fc.project3.mycontact.configuration.searializer.BirthdaySearializer;
import com.fc.project3.mycontact.domain.dto.Birthday;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JavaConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule()); // LocalDate 전체가 출력됨
        objectMapper.registerModule(new JavaTimeModule()); // [2019,1,12] 로변경됨

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 2019-1-12

        return objectMapper;
    }

    static class BirthdayModule extends SimpleModule {
        BirthdayModule() {
            super();
            addSerializer(Birthday.class, new BirthdaySearializer());
        }
    }

}
