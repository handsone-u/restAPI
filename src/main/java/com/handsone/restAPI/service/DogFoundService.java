package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.repository.DogFoundRepository;
import com.handsone.restAPI.domain.File;
import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.repository.MemberRepository;
import com.handsone.restAPI.global.request.DogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.domain.DogFound.createDogFound;
import static com.handsone.restAPI.domain.File.createFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DogFoundService {

    private final DogFoundRepository dogFoundRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    public DogFound upload(DogDto dogDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(dogDto.getMemberId()).get();
        DogFound dogFound = createDogFound(member, dogDto);
        dogFound = dogFoundRepository.save(dogFound);
        for (MultipartFile multiFile : files) {
            File file = createFile(dogFound, multiFile);
            String savePath = System.getProperty("user.dir")+"/src/main/resources/found"+dogFound.getId();

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
        }
        return dogFound;
    }

    @Transactional(readOnly = true)
    public Slice<DogFound> findAllByBoardStatusNormal(PageRequest pageRequest) {
        return dogFoundRepository.findAllByBoardStatus(BoardStatus.NORMAL, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<DogFound> findAll() {
        return dogFoundRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DogFound> findAllByMemberId(Long memberId) {
        return dogFoundRepository.findAllByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<DogFound> findById(Long id) {
        return dogFoundRepository.findById(id);
    }
}
