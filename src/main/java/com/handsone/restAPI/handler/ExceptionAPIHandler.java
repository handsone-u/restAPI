package com.handsone.restAPI.handler;

import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.exception.FileDownloadException;
import com.handsone.restAPI.exception.FileUploadException;
import com.handsone.restAPI.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionAPIHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ClientException.class})
    protected ResponseEntity<ErrorResponse> clientErrorHandle(ClientException e) {
        log.debug(e.getErrorCode().getDetail());
        log.debug(e.getMessage());

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {FileDownloadException.class})
    protected ResponseEntity<ErrorResponse> fileDownLoadError(FileDownloadException e) {
        log.error(e.getErrorCode().getDetail());
        log.error(e.getMessage());

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {FileUploadException.class})
    protected ResponseEntity<ErrorResponse> fileUploadError(FileUploadException e) {
        log.error(e.getErrorCode().getDetail());
        log.error(e.getMessage());

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
