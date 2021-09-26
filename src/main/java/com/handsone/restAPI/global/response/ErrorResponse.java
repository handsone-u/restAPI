package com.handsone.restAPI.global.response;

import com.handsone.restAPI.error.ErrorCode;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class ErrorResponse extends Response{
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .localDateTime(LocalDateTime.now())
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build());
    }

}
