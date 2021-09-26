package com.handsone.restAPI.domain;

import com.handsone.restAPI.dto.DogDto;
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
public abstract class Dog {

    @Id @GeneratedValue
    @Column(name = "dog_id")
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    protected Member member;

    protected String content;
    @Embedded
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

    public Dog(Long id, Member member, String content, Address address, BoardStatus boardStatus, Gender gender) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.address = address;
        this.boardStatus = BoardStatus.NORMAL;
        this.gender = gender;
    }

    public Dog(DogDto dogDto) {
        this.content = dogDto.getContent();
        this.address = dogDto.getAddress();
        this.boardStatus = BoardStatus.NORMAL;
        this.gender = dogDto.getGender();
    }
}
