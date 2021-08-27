package com.handsone.restAPI.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FileUploadResponse {
    private String fileName;
    private String fileDownLoadUrl;
    private String fileType;
    private long size;
}
