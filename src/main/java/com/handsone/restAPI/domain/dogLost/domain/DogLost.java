package com.handsone.restAPI.domain.dogLost.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.Dog;
import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.file.domain.File;
import com.handsone.restAPI.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","title", "dogName"})
public class DogLost extends Dog {

    private String title;
    private String dogName;

    @Id
    @GeneratedValue
    @Column(name = "doglost_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "dogLost")
    @JsonIgnore
    private List<File> fileList = new ArrayList<>();

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeBoardStatus(BoardStatus boardStatus) {
        this.boardStatus = boardStatus;
    }
    public void changeGender(Gender gender) {
        this.gender = gender;
    }
    public void changeDogName(String dogName) {
        this.dogName = dogName;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public void addFile(File file) {
        fileList.add(file);
    }

    public DogLost(DogDto dogDto) {
        super(dogDto);
        this.title = dogDto.getTitle();
        this.dogName = dogDto.getDogName();
    }

    public static DogLost createDogLost(Member member, DogDto dogDto) {
        DogLost dogLost = new DogLost(dogDto);
        dogLost.setBoardStatus(BoardStatus.NORMAL);
        dogLost.setMember(member);
        member.addDogLost(dogLost);
        return dogLost;
    }
}