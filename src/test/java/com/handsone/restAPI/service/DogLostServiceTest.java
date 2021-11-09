package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.ImageFile;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.infra.address.Address;
import com.handsone.restAPI.repository.DogLostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class DogLostServiceTest {
    @Mock
    DogLostRepository dogLostRepository;
    @InjectMocks
    DogLostService dogLostService;

    static ArrayList<DogDto> dogDTOs = new ArrayList<>();

    @BeforeAll
    public static void initUpload() {
        ArrayList<ImageFile> mocked = new ArrayList<>();
        mocked.add(Mockito.mock(ImageFile.class));

        Member mem1 = new Member(1L, "user1", "pass1", "nick1", new Address("gu"));
        Member mem2 = new Member(2L, "user2", "pass2", "nick2", new Address("gu2"));

        for (int i = 0; i < 2; i++) {
            DogDto dtoMem1 = new DogDto(0L, mem1.getId(), mem1, mocked, mem1.getNickName(), "dogName"+i,
                    "title", "content", Gender.values()[i % Gender.values().length], "dogBreed"+i,
                    new Address("go2"), LocalDateTime.now(), LocalDateTime.now());
            DogDto dtoMem2 = new DogDto(0L, mem2.getId(), mem2, mocked, mem2.getNickName(), "dogName"+i*2,
                    "title", "content", Gender.values()[i % Gender.values().length], "dogBreed"+i*2,
                    new Address("go2"), LocalDateTime.now(), LocalDateTime.now());

            dogDTOs.add(dtoMem1);
            dogDTOs.add(dtoMem2);
        }
    }

    @Test
    public void uploadTest() {

    }
}
