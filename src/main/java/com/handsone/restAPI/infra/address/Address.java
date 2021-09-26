package com.handsone.restAPI.infra.address;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String gu;
    private String dong;
}
