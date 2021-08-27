package com.handsone.restAPI.infra.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable @ToString @Getter @Setter
@NoArgsConstructor
public class Address {
    private String gu;
    private String dong;

    public Address(String si, String gu, String dong) {
        this.gu = gu;
        this.dong = dong;
    }

    public Address(String gu, String dong) {
        this.gu = gu;
        this.dong = dong;
    }
}
