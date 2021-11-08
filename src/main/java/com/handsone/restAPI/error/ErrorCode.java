package com.handsone.restAPI.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400

    // 401
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    // 404
    NOTFOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOTFOUND_DOG(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    NOTFOUND_FILE(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다,"),
    // 409
    DUPLICATE_RESOURCE(CONFLICT, "중복된 데이터가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
