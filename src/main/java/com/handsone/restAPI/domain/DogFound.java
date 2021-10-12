package com.handsone.restAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.dto.DogDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogFound extends Dog {

    @OneToMany(mappedBy = "dogFound") @JsonIgnore
    private List<ImageFile> imageFileList = new ArrayList<>();

    public DogFound(DogDto dogDto) {
        super(dogDto);
    }

    public static DogFound createDogFound(Member member, DogDto dogDto) {
        DogFound dogFound = new DogFound(dogDto);
        dogFound.setMember(member);
        member.addDogFound(dogFound);
        return dogFound;
    }
}
