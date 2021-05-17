package com.handsone.restAPI.domain.file.dto;

import com.handsone.restAPI.domain.file.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String origFileName;
    private String fileName;
    private String filePath;

    public File toEntity() {
        return new File(id, origFileName, fileName, filePath);
    }

    @Builder
    public FileDto(Long id, String origFileName, String fileName, String filePath) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
