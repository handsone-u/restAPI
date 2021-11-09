package com.handsone.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.infra.address.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogFound extends Dog {

    @OneToMany(mappedBy = "dogFound") @JsonIgnore
    private List<ImageFile> imageFileList = new ArrayList<>();

    public DogFound(Long id, Member member, String content, Address address, BoardStatus boardStatus, Gender gender,
                    String dogBreed, LocalDateTime regDate, LocalDateTime lastModifiedDate,
                    List<ImageFile> imageFileList) {
        super(id, member, content, address, boardStatus, gender, dogBreed, regDate, lastModifiedDate);
        this.imageFileList = imageFileList;
    }

    public DogFound(List<ImageFile> imageFileList) {
        this.imageFileList = imageFileList;
    }

    public static DogFound createDogFound(Member member, DogDto dogDto) {
        DogFound dogFound = dogDto.toEntityFound();
        dogFound.setMember(member);
        member.addDogFound(dogFound);
        return dogFound;
    }
}
