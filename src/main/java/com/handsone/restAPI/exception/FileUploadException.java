package com.handsone.restAPI.exception;


import com.handsone.restAPI.error.ErrorCode;
import lombok.Getter;

@Getter
public class FileUploadException extends RuntimeException{
    private ErrorCode errorCode;

    public FileUploadException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
