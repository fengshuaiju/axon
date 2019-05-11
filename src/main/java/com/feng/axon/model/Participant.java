package com.feng.axon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(fluent = true)
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Participant {

    private ChatterId chatterId;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    private String name;

    @JsonCreator
    public Participant(@JsonProperty(value = "orderId", required = true) ChatterId orderId,
                       @JsonProperty(value = "sex", required = true) Sex sex,
                       @JsonProperty(value = "name", required = true) String name) {
        this.chatterId = orderId;
        this.sex = sex;
        this.name = name;
    }

}
