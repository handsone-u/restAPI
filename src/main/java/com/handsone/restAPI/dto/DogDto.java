package com.handsone.restAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.domain.*;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private final List<String> fileUris = new ArrayList<>();
    private String nickName;
    private String dogName;
    private String title;
    private String content;
    private Gender gender;
    private String dogBreed;
    private Address address;
    private LocalDateTime regDate;
    private LocalDateTime lastModifiedDate;

    public DogLost toEntityLost() {
        return new DogLost(id, member, content, address, BoardStatus.NORMAL, gender, dogBreed, regDate,
                lastModifiedDate, title, dogName, imageFileList);
    }

    public DogFound toEntityFound() {
        return new DogFound(id, member, content, address, BoardStatus.NORMAL, gender, dogBreed, regDate,
                lastModifiedDate, imageFileList);
    }
    
    public void setMemberProperties() {
        this.memberId = member.getId();
        this.nickName = member.getNickName();
    }

    public void setFileProperties(String uri) {
        imageFileList.forEach((image) -> {
            this.fileIds.add(image.getId());
            this.fileUris.add(uri + File.separator + image.getFileName());});
    }

    public DogDto setMemberAndFileProperties(String uri) {
        this.setMemberProperties();
        this.setFileProperties(uri);
        return this;
    }
}
