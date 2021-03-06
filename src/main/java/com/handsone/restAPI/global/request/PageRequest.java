package com.handsone.restAPI.global.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter @Setter @ToString
@NoArgsConstructor
public class PageRequest {

    private int page;
    private int size;
    private Sort.Direction direction = Sort.Direction.DESC;
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }
    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }
    // getter
    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page -1, size, direction, "regDate");
    }
}
