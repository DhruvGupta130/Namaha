package com.trulydesignfirm.namaha.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(indexes = @Index(name = "idx_image_file_public_id", columnList = "publicId"))
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fileName;
    private String publicId;
    private String fileUrl;
    private long fileSize;

    @CreationTimestamp
    private Date uploadDate;

    private String fileType;
}
