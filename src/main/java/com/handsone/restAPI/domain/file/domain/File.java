package com.handsone.restAPI.domain.file.domain;

import com.handsone.restAPI.domain.dogFound.domain.DogFound;
import com.handsone.restAPI.domain.dogLost.domain.DogLost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

@Entity @Getter
@NoArgsConstructor
public class File {
    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String origFileName;
    private String fileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doglost_id")
    private DogLost dogLost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogfound_id")
    private DogFound dogFound;

    public File(Long id, String origFileName, String fileName, String filePath) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public File (DogLost dogLost) {
        this.dogLost = dogLost;
    }
    public File (DogFound dogFound){
        this.dogFound = dogFound;
    }

    public void setNames(MultipartFile multiFile) {
        this.origFileName = multiFile.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        this.fileName = uuid.toString() + "_" + this.origFileName;
    }

    public static File createFile(DogLost dogLost, MultipartFile multiFile) {
        File file = new File(dogLost);
        file.setNames(multiFile);
        return file;
    }
    public static File createFile(DogFound dogFound, MultipartFile multiFile) {
        File file = new File(dogFound);
        file.setNames(multiFile);
        return file;
    }
}
