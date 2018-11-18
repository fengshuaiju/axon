package com.feng.axon.utils;

import javax.persistence.AttributeConverter;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class SetToStringConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        return attribute == null ? "" : attribute.stream().collect(joining(";"));
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(dbData.split(";")).collect(toSet());
    }
}
