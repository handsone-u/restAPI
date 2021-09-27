package com.handsone.restAPI.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class ImageFile {
    @Id @GeneratedValue
    @Column(name = "image_file_id")
    private Long id;

    private String origFileName;
    private String fileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_lost_id")
    private DogLost dogLost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_found_id")
    private DogFound dogFound;

    public ImageFile(Long id, String origFileName, String fileName, String filePath) {
        this.id = id;
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public ImageFile(DogLost dogLost) {
        this.dogLost = dogLost;
    }
    public ImageFile(DogFound dogFound){
        this.dogFound = dogFound;
    }

    public void setFileNames(MultipartFile multiFile, String fileName) {
        this.origFileName = multiFile.getOriginalFilename();
        this.fileName = fileName + "." + origFileName.split("\\.")[1];
    }

    public static ImageFile createFile(DogLost dogLost, MultipartFile multiFile, String fileName) {
        ImageFile imageFile = new ImageFile(dogLost);
        imageFile.setFileNames(multiFile, fileName);
        dogLost.getImageFileList().add(imageFile);
        return imageFile;
    }

    public static ImageFile createFile(DogFound dogFound, MultipartFile multiFile, String fileName) {
        ImageFile imageFile = new ImageFile(dogFound);
        imageFile.setFileNames(multiFile, fileName);
        dogFound.getImageFileList().add(imageFile);
        return imageFile;
    }
}
