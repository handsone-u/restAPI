package com.handsone.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogLost extends Dog {

    private String title;
    private String dogName;

    @OneToMany(mappedBy = "dogLost")
    private List<ImageFile> imageFileList = new ArrayList<>();

    public DogLost(Long id, Member member, String content, Address address, BoardStatus boardStatus, Gender gender,
                   String dogBreed, LocalDateTime regDate, LocalDateTime lastModifiedDate, String title, String dogName,
                   List<ImageFile> imageFileList) {
        super(id, member, content, address, boardStatus, gender, dogBreed, regDate, lastModifiedDate);
        this.title = title;
        this.dogName = dogName;
        this.imageFileList = imageFileList;
    }

    public static DogLost createDogLost(Member member, DogDto dogDto) {
        DogLost dogLost = dogDto.toEntityLost();
        dogLost.setMember(member);
        member.addDogLost(dogLost);
        return dogLost;
    }
}