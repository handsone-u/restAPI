package com.handsone.restAPI.domain.member.dto;

import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String userId;
    private String password;
    private String nickname;
    private Address address;

    public Member toMember() {
        return new Member(this.userId, this.password, this.nickname, this.address);
    }

    public void erasePassword() { this.password = ""; }

    public static MemberDto toMemberDto(Member member) {
        return new MemberDto(member.getId(), member.getUserId(), "hide", member.getNickName(), member.getAddress());
    }
}
