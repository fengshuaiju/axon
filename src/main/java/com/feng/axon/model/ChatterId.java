package com.feng.axon.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@Accessors(fluent = true)
@Access(AccessType.FIELD)
public class ChatterId {

    @Column(name = "chatter_id", columnDefinition = "uuid")
    private UUID value;

    @JsonValue
    @Override
    public String toString(){
        return value.toString();
    }

    public ChatterId(String value){
        this.value = UUID.fromString(value);
    }

    public ChatterId(UUID value){
        this.value = value;
    }

    public static ChatterId newId(){
        return new ChatterId(UUID.randomUUID());
    }

}
