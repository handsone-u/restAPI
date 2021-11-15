package com.handsone.restAPI.domain;

import com.handsone.restAPI.infra.address.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    public DogLost createDogLost(Member member) {
        this.setMember(member);
        member.addDogLost(this);
        return this;
    }
}