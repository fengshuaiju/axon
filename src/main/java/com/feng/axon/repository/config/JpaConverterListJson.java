package com.feng.axon.repository.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.Set;

@Component
public class JpaConverterListJson implements AttributeConverter<Set, String> {

    private final ObjectMapper objectMapper;

    public JpaConverterListJson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Set object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    @Override
    public Set convertToEntityAttribute(String content) {
        return objectMapper.readValue(content, Set.class);
    }
}
