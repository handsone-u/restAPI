package com.handsone.restAPI.dto;

import com.handsone.restAPI.domain.ImageFile;
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

    public ImageFile toEntity() {
        return new ImageFile(id, origFileName, fileName, filePath);
    }

    @Builder
    public FileDto(Long id, String origFileName, String fileName, String filePath) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
