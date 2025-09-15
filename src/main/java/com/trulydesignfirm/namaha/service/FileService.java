package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    ImageFile saveFile(MultipartFile file) throws IOException;

    String deleteFile(String fileName) throws IOException;
}
