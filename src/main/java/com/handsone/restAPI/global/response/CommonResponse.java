package com.handsone.restAPI.global.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> extends Response {
    private int count;
    private T data;

    public CommonResponse(T data) {
        this.data = data;
        if(data instanceof List) {
            this.count = ((List<?>)data).size();
        } else {
            this.count = 1;
        }
    }
}

