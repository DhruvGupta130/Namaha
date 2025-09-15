package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ImageFile saveFile(MultipartFile file);

    String deleteFile(String fileName);
}
