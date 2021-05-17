package com.handsone.restAPI.domain;

import com.handsone.restAPI.global.request.DogDto;
import com.handsone.restAPI.infra.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Dog {

    protected String content;
    protected Address address;

    @Enumerated(value = EnumType.STRING)
    protected BoardStatus boardStatus = BoardStatus.NORMAL;

    @Enumerated(value = EnumType.STRING)
    protected Gender gender;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime regDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    public Dog(String content, Address address, BoardStatus boardStatus, Gender gender) {
        this.content = content;
        this.address = address;
        this.boardStatus = boardStatus;
        this.gender = gender;
    }

    public Dog(DogDto dogDto) {
        this.content = dogDto.getContent();
        this.address = dogDto.getAddress();
        this.gender = dogDto.getGender();
    }
}
