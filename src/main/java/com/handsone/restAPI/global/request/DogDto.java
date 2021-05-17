package com.handsone.restAPI.global.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handsone.restAPI.domain.BoardStatus;
import com.handsone.restAPI.domain.Gender;
import com.handsone.restAPI.domain.dogFound.domain.DogFound;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(of = {"id", "boardStatus", "memberId","title", "content","nickName", "dogName", "gender"})
@NoArgsConstructor @AllArgsConstructor
public class DogDto {
    private Long id;
    private BoardStatus boardStatus;
    private Long memberId;
    private String nickName;
    @JsonIgnore
    private List<Long> fileIdList = new ArrayList<>();
    private String title;
    private String content;
    private String dogName;
    private Gender gender;
    private Address address;
    private LocalDateTime regDate;
    private LocalDateTime lastModifiedDate;

    public void setFileIdList(List<Long> fileIdList) {
        this.fileIdList = fileIdList;
    }

    public DogDto(Long memberId, String nickName, String title, String content, String dogName,
                  Gender gender, Address address) {
        this.boardStatus = boardStatus;
        this.memberId = memberId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.dogName = dogName;
        this.gender = gender;
        this.address = address;
    }

    public DogDto(Long id, BoardStatus boardStatus, Long memberId, String nickName, String title, String content,
                  String dogName, Gender gender, Address address , LocalDateTime regDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.boardStatus = boardStatus;
        this.memberId = memberId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.dogName = dogName;
        this.gender = gender;
        this.address = address;
        this.regDate = regDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public DogDto(Long id, BoardStatus boardStatus, Long memberId, String nickName, String content, Gender gender,
                  Address address ,LocalDateTime regDate, LocalDateTime lastModifiedDate) {
        this.boardStatus = boardStatus;
        this.memberId = memberId;
        this.nickName = nickName;
        this.content = content;
        this.gender = gender;
        this.address = address;
        this.regDate = regDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static DogDto toDogDto(DogLost dogLost) {
        return new DogDto(dogLost.getId(), dogLost.getBoardStatus(), dogLost.getMember().getId(), dogLost.getMember().getNickName(),
                dogLost.getTitle(), dogLost.getContent(), dogLost.getDogName(), dogLost.getGender(), dogLost.getAddress(),
                dogLost.getRegDate(), dogLost.getLastModifiedDate());
    }
    public static DogDto toDogDto(DogFound dogFound) {
        return new DogDto(dogFound.getId(), dogFound.getBoardStatus(), dogFound.getMember().getId(), dogFound.getMember().getNickName(),
                dogFound.getContent(), dogFound.getGender(), dogFound.getAddress(),
                dogFound.getRegDate(), dogFound.getLastModifiedDate());
    }
}
