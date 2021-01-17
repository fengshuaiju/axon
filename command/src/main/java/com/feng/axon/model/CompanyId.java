package com.feng.axon.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class CompanyId implements Serializable {

    @Column(name = "company_id", columnDefinition = "uuid")
    private UUID value;

    @JsonValue
    @Override
    public String toString(){
        return value.toString();
    }

    public CompanyId(String value){
        this.value = UUID.fromString(value);
    }

    public CompanyId(UUID value){
        this.value = value;
    }

    public static CompanyId newId(){
        return new CompanyId(UUID.randomUUID());
    }

}
