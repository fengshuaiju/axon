package com.feng.axon.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class ChatterId implements Serializable, Comparable<ChatterId> {

    @Column(name = "chatter_id", columnDefinition = "uuid")
    private UUID value;

    @JsonValue
    @Override
    public String toString() {
        return value.toString();
    }

    public ChatterId(String value) {
        this.value = UUID.fromString(value);
    }

    public ChatterId(UUID value) {
        this.value = value;
    }

    public static ChatterId newId() {
        return new ChatterId(UUID.randomUUID());
    }

    @Override
    public int compareTo(ChatterId other) {
        return this.toString().compareTo(other.toString());
    }
}
