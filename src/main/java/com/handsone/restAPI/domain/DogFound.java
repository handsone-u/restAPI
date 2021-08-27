package com.handsone.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.global.request.DogDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogFound extends Dog {

    @Id
    @GeneratedValue
    @Column(name = "dogfound_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "dogFound")
    @JsonIgnore
    private List<File> fileList = new ArrayList<>();

    public void changeBoardStatus(BoardStatus boardStatus) {
        this.boardStatus = boardStatus;
    }
    public void changeGender(Gender gender) {
        this.gender = gender;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public DogFound(DogDto dogDto) {
        super(dogDto);
    }

    public static DogFound createDogFound(Member member, DogDto dogDto) {
        DogFound dogFound = new DogFound(dogDto);
        dogFound.setBoardStatus(BoardStatus.NORMAL);
        dogFound.setMember(member);
        return dogFound;
    }
}
