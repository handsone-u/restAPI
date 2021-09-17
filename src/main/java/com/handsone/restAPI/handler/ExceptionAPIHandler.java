package com.handsone.restAPI.handler;

import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.global.response.ErrorResponse;
import com.handsone.restAPI.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.handsone.restAPI.error.ErrorCode.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class ExceptionAPIHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ClientException.class})
    protected ResponseEntity<? extends Response> duplicatedExceptionHandler() {

        return ResponseEntity
                .status(DUPLICATE_RESOURCE.getHttpStatus())
                .body(ErrorResponse.builder()
                        .errorMessage(DUPLICATE_RESOURCE.getDetail())
                        .errorCode(DUPLICATE_RESOURCE.getHttpStatus().toString())
                        .build());
    }
}
