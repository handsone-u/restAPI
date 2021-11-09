package com.handsone.restAPI.domain;

import com.handsone.restAPI.dto.DogDto;
import com.handsone.restAPI.infra.address.Address;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @AllArgsConstructor
public abstract class Dog {

    @Id @GeneratedValue
    @Column(name = "dog_id")
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    protected Member member;

    protected String content;
    @Embedded
    protected Address address;

    @Enumerated(value = EnumType.STRING)
    protected BoardStatus boardStatus = BoardStatus.NORMAL;

    @Enumerated(value = EnumType.STRING)
    protected Gender gender;

    private String dogBreed;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime regDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
