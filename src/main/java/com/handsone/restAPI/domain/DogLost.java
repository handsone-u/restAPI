package com.handsone.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.dto.DogDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogLost extends Dog {

    private String title;
    private String dogName;

    @OneToMany(mappedBy = "dogLost") @JsonIgnore
    private List<File> fileList = new ArrayList<>();

    public DogLost(DogDto dogDto) {
        super(dogDto);
        this.title = dogDto.getTitle();
        this.dogName = dogDto.getDogName();
    }

    public static DogLost createDogLost(Member member, DogDto dogDto) {
        DogLost dogLost = new DogLost(dogDto);
        dogLost.setMember(member);
        member.addDogLost(dogLost);
        return dogLost;
    }
}