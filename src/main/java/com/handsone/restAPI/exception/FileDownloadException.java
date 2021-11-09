package com.handsone.restAPI.exception;

import com.handsone.restAPI.error.ErrorCode;
import lombok.Getter;

@Getter
public class FileDownloadException extends RuntimeException {
    private ErrorCode errorCode;

    public FileDownloadException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
