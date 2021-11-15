package com.handsone.restAPI.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400

    // 401
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "인증에 실패하였습니다."),
    // 404
    NOTFOUND_MEMBER(NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOTFOUND_DOG(NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    NOTFOUND_FILE(NOT_FOUND, "해당 파일을 찾을 수 없습니다,"),
    // 409
    DUPLICATE_RESOURCE(CONFLICT, "중복된 데이터가 존재합니다."),

    // 500
    FILE_WRITE_ERROR(INTERNAL_SERVER_ERROR, "파일 저장중 오류가 발상했습니다. 잠시 후 다시 시도해 주세요."),
    FILE_READ_ERROR(INTERNAL_SERVER_ERROR,"파일을 불러오는중 오류가 발상했습니다. 잠시 후 다시 시도해 주세요.");

    private final HttpStatus httpStatus;
    private final String detail;
}
