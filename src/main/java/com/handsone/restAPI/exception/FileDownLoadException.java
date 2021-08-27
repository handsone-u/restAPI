package com.handsone.restAPI.exception;

public class FileDownLoadException extends RuntimeException{
    public FileDownLoadException(String message) {
        super(message);
    }

    public FileDownLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
