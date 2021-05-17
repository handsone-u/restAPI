package com.handsone.restAPI.domain.dogLost.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.file.domain.File;
import com.handsone.restAPI.domain.file.service.FileService;
import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import com.handsone.restAPI.domain.dogLost.repository.DogLostRepository;
import com.handsone.restAPI.domain.member.domain.Member;
import com.handsone.restAPI.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.dogLost.domain.DogLost.createDogLost;
import static com.handsone.restAPI.domain.file.domain.File.createFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DogLostService {

    private final DogLostRepository dogLostRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    public DogLost upload(DogDto dogDto, List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Member member = memberRepository.findById(dogDto.getMemberId()).get();
        DogLost dogLost = createDogLost(member, dogDto);
        dogLost = dogLostRepository.save(dogLost);
        for (MultipartFile multiFile : files) {
            File file = createFile(dogLost, multiFile);
            String savePath = System.getProperty("user.dir");

            if (!new java.io.File(savePath).exists()) {
                try {
                    new java.io.File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "\\" + file.getFileName();
            multiFile.transferTo(new java.io.File(filePath));
            file.setFilePath(filePath);

            file = fileService.saveFile(file);
        }
        return dogLost;
    }

    @Transactional(readOnly = true)
    public Slice<DogLost> findAllByBoardStatusNormal(PageRequest pageRequest) {
        return dogLostRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<DogLost> findAll() {
        return dogLostRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DogLost> findAllByMemberId(Long memberId) {
        return dogLostRepository.findAllByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<DogLost> findById(Long id) {
        return dogLostRepository.findById(id);
    }
}
