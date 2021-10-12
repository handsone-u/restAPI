package com.handsone.restAPI.dto;

import com.handsone.restAPI.domain.ImageFile;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FileDto {

    private Long id;
    private String origFileName;
    private String fileName;
    private String filePath;
    private String fileUri;
}
