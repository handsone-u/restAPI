package com.handsone.restAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.domain.*;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DogDto {
    private Long id;
    private Long memberId;
    @JsonIgnore
    private Member member;
    @JsonIgnore
    private List<ImageFile> imageFileList = new ArrayList<>();
    private final List<Long> fileIds = new ArrayList<>();
    private final List<String> fileUri = new ArrayList<>();
    private String nickName;
    private String dogName;
    private String title;
    private String content;
    private Gender gender;
    private Address address;
    private LocalDateTime regDate;
    private LocalDateTime lastModifiedDate;

    public DogDto(DogLost dogLost) {
        DogDto dto = DogDto.builder()
                .id(dogLost.getId())
                .memberId(dogLost.getMember().getId())
                .member(dogLost.getMember())
                .imageFileList(dogLost.getImageFileList())
                .nickName(dogLost.getMember().getNickName())
                .dogName(dogLost.getDogName())
                .title(dogLost.getTitle())
                .content(dogLost.getContent())
                .gender(dogLost.getGender())
                .address(dogLost.getAddress())
                .regDate(dogLost.getRegDate())
                .lastModifiedDate(dogLost.getLastModifiedDate())
                .build();
        dogLost.getImageFileList().forEach(d ->{
            fileIds.add(d.getId());
            fileUri.add(d.getFilePath());
        });
    }

    public DogDto setMemberProperties() {
        this.memberId = member.getId();
        this.nickName = member.getNickName();
        return this;
    }

    public DogDto setFileProperties(String uri) {
        imageFileList.forEach((image) -> {
            this.fileIds.add(image.getId());
            this.fileUri.add(uri + "/" + image.getFileName());});
        return this;
    }

    public DogDto setMemberAndFileProperties(String uri) {
        this.setMemberProperties();
        this.setFileProperties(uri);
        return this;
    }
}
