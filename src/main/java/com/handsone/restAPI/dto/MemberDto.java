package com.handsone.restAPI.dto;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    private Long id;
    private String userId;
    private String password;
    private String nickname;
    private Address address;

    public Member toMember() {
        return new Member(this.userId, this.password, this.nickname, this.address);
    }

    public void erasePassword() { this.password = ""; }

    public Member toEntity() {
        return new Member(id, userId, password, nickname, address);
    }
}
