package com.feng.axon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Accessors(fluent = true)
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Participant1 {
    @Column(name = "name")
    private String name;
}
