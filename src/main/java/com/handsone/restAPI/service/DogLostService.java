package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.File;
import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.repository.DogLostRepository;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.DogLost.createDogLost;
import static com.handsone.restAPI.domain.File.createFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DogLostService {

    private final DogLostRepository dogLostRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Transactional(readOnly = false)
    public DogLost upload(DogDto dogDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(dogDto.getMemberId()).get();
        DogLost dogLost = createDogLost(member, dogDto);
        dogLost = dogLostRepository.save(dogLost);
        for (MultipartFile multiFile : files) {
            File file = createFile(dogLost, multiFile);
            String savePath = System.getProperty("user.dir")+"/src/main/resources/lost"+dogLost.getId();

            if (!new java.io.File(savePath).exists()) {
                try {
                    new java.io.File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "/" + file.getFileName();
            multiFile.transferTo(new java.io.File(filePath));
            file.setFilePath(filePath);

            file = fileService.saveFile(file);
            dogLost.addFile(file);
        }
        return dogLost;
    }

    public Slice<DogLost> findAllByBoardStatusNormal(Pageable pageRequest) {
        return dogLostRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
    }

    public Long count(){
        return dogLostRepository.count();
    }

    public List<DogLost> findAll() {
        return dogLostRepository.findAll();
    }

    public List<DogLost> findAllByMemberId(Long memberId) {
        return dogLostRepository.findAllByMemberId(memberId);
    }

    public Optional<DogLost> findById(Long id) {
        return dogLostRepository.findById(id);
    }
}
