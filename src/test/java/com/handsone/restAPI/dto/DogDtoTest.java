package com.handsone.restAPI.dto;

import com.handsone.restAPI.domain.*;
import com.handsone.restAPI.infra.address.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DogDtoTest {
    static ArrayList<DogDto> dogDTOs = new ArrayList<>();

    @BeforeAll
    public static void init() {
        ArrayList<ImageFile> mocked = new ArrayList<>();
        mocked.add(Mockito.mock(ImageFile.class));

        Member member = new Member(1L, "user1", "pass1", "nick1", new Address("gu"));
        DogDto dto = new DogDto(0L, member.getId(), member, mocked, member.getNickName(), "dogName",
                "title", "content", Gender.MALE, "dogBreed",
                new Address("go2"), LocalDateTime.now(), LocalDateTime.now());

        dogDTOs.add(dto);
        assertThat(dogDTOs).isNotEmpty();
    }

    @Test
    public void convertDtoToEntityLost() {
        for (DogDto dogDTO : dogDTOs) {
            DogLost dogLost = dogDTO.toEntityLost();

            assertThat(dogDTO.getId()).isEqualTo(dogLost.getId());
            assertThat(dogDTO.getMember()).isEqualTo(dogLost.getMember());
            assertThat(dogDTO.getImageFileList()).isEqualTo(dogLost.getImageFileList());
            assertThat(dogDTO.getNickName()).isEqualTo(dogLost.getMember().getNickName());
            assertThat(dogDTO.getDogName()).isEqualTo(dogLost.getDogName());
            assertThat(dogDTO.getTitle()).isEqualTo(dogLost.getTitle());
            assertThat(dogDTO.getContent()).isEqualTo(dogLost.getContent());
            assertThat(dogDTO.getGender()).isEqualTo(dogLost.getGender());
            assertThat(dogDTO.getDogBreed()).isEqualTo(dogLost.getDogBreed());
            assertThat(dogDTO.getAddress().getGu()).isEqualTo(dogLost.getAddress().getGu());
            assertThat(dogDTO.getRegDate()).isEqualTo(dogLost.getRegDate());
            assertThat(dogDTO.getLastModifiedDate()).isEqualTo(dogLost.getLastModifiedDate());
        }
    }

    @Test
    public void convertDtoToEntityFound() {
        for (DogDto dogDTO : dogDTOs) {
            DogFound dogFound = dogDTO.toEntityFound();

            assertThat(dogDTO.getId()).isEqualTo(dogFound.getId());
            assertThat(dogDTO.getMember()).isEqualTo(dogFound.getMember());
            assertThat(dogDTO.getImageFileList()).isEqualTo(dogFound.getImageFileList());
            assertThat(dogDTO.getNickName()).isEqualTo(dogFound.getMember().getNickName());
            assertThat(dogDTO.getContent()).isEqualTo(dogFound.getContent());
            assertThat(dogDTO.getGender()).isEqualTo(dogFound.getGender());
            assertThat(dogDTO.getDogBreed()).isEqualTo(dogFound.getDogBreed());
            assertThat(dogDTO.getAddress().getGu()).isEqualTo(dogFound.getAddress().getGu());
            assertThat(dogDTO.getRegDate()).isEqualTo(dogFound.getRegDate());
            assertThat(dogDTO.getLastModifiedDate()).isEqualTo(dogFound.getLastModifiedDate());
        }
    }
}