package com.handsone.restAPI.exception;

import com.handsone.restAPI.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ClientException extends RuntimeException{
    private final ErrorCode errorCode;

    public ClientException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ClientException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
