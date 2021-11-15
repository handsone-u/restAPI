package com.handsone.restAPI.domain;

import com.handsone.restAPI.infra.address.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogFound extends Dog {

    @OneToMany(mappedBy = "dogFound")
    private List<ImageFile> imageFileList = new ArrayList<>();

    public DogFound(Long id, Member member, String content, Address address, BoardStatus boardStatus, Gender gender,
                    String dogBreed, LocalDateTime regDate, LocalDateTime lastModifiedDate,
                    List<ImageFile> imageFileList) {
        super(id, member, content, address, boardStatus, gender, dogBreed, regDate, lastModifiedDate);
        this.imageFileList = imageFileList;
    }

    public DogFound createDogFound(Member member) {
        this.setMember(member);
        member.addDogFound(this);
        return this;
    }
}
