package com.handsone.restAPI.infra.address;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable @Getter @Setter
@NoArgsConstructor
public class Address {
    private String gu;

    public Address(String gu) {
        this.gu = gu;
    }
}
