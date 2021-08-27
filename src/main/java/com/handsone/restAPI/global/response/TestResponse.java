package com.handsone.restAPI.global.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestResponse<T> extends Response {
    private boolean success;
    private String message;
    private int count;
    private T data;

    public TestResponse(T data) {
        this.data = data;
        this.success = true;
        if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }
}
