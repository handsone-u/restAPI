package com.handsone.restAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    private Long id;
    private String userId;
    @JsonIgnore
    private String password;
    private String nickName;
    private Address address;

    public Member toEntity() {
        return new Member(id, userId, password, nickName, address);
    }
}
