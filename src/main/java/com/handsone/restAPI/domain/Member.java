package com.handsone.restAPI.domain;

import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(of = {"id", "userId", "password","nickName", "address"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(updatable = false)
    private String userId;
    private String password;
    private String nickName;
    @Embedded @Column(name = "home_address")
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<DogLost> dogLostList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<DogFound> dogFoundList = new ArrayList<>();

    public Member changePassword(String password) {
        this.password = password;
        return this;
    }
    public Member changeNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void addDogLost(DogLost dogLost) {
        dogLostList.add(dogLost);
    }
    public void addDogFound(DogFound dogFound) {
        dogFoundList.add(dogFound);
    }

    public Member(Long id, String userId, String password, String nickName, Address address) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
    }

    public Member(String userId, String password, String nickName, Address address) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
    }
}
