package com.handsone.restAPI.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {
    private String uploadDir;
}
