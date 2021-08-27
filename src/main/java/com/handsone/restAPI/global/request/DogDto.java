package com.handsone.restAPI.global.request;

import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString(of = {"id", "boardStatus", "memberId","title", "content","nickName", "dogName", "gender"})
@NoArgsConstructor @AllArgsConstructor
public class DogDto {
    private Long dogId;
    private Long memberId;
    private List<Long> fileIds;
    private String nickName;
    private String dogName;
    private String title;
    private String content;
    private Gender gender;
    private Address address;
    private LocalDateTime regDate;

    public DogDto(DogLost dogLost) {
        this.dogId = dogLost.getId();
        this.memberId = dogLost.getMember().getId();
        this.fileIds = dogLost.getFileList().stream().map(file -> new Long(file.getId())).collect(Collectors.toList());
        this.nickName = dogLost.getMember().getNickName();
        this.dogName = dogLost.getDogName();
        this.title = dogLost.getTitle();
        this.content = dogLost.getContent();
        this.gender = dogLost.getGender();
        this.address = dogLost.getAddress();
        this.regDate = dogLost.getRegDate();
    }

    public void setFileIdList(List<Long> fileIdList) {
        this.fileIds = fileIdList;
    }

    public DogDto(Long memberId, String nickName, String title, String content, String dogName,
                  Gender gender, Address address) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.dogName = dogName;
        this.gender = gender;
        this.address = address;
    }

    public DogDto(Long id, BoardStatus boardStatus, Long memberId, String nickName, String title, String content,
                  String dogName, Gender gender, Address address , LocalDateTime regDate) {
        this.dogId = id;
        this.memberId = memberId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.dogName = dogName;
        this.gender = gender;
        this.address = address;
        this.regDate = regDate;
    }

    public DogDto(Long id, BoardStatus boardStatus, Long memberId, String nickName, String content, Gender gender,
                  Address address ,LocalDateTime regDate) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.content = content;
        this.gender = gender;
        this.address = address;
        this.regDate = regDate;
    }

    public static DogDto toDogDto(DogLost dogLost) {
        return new DogDto(dogLost.getId(), dogLost.getBoardStatus(), dogLost.getMember().getId(), dogLost.getMember().getNickName(),
                dogLost.getTitle(), dogLost.getContent(), dogLost.getDogName(), dogLost.getGender(), dogLost.getAddress(),
                dogLost.getRegDate());
    }
    public static DogDto toDogDto(DogFound dogFound) {
        return new DogDto(dogFound.getId(), dogFound.getBoardStatus(), dogFound.getMember().getId(), dogFound.getMember().getNickName(),
                dogFound.getContent(), dogFound.getGender(), dogFound.getAddress(),
                dogFound.getRegDate());
    }
}
