package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Response saveFile(MultipartFile file);

    Response deleteFile(String fileName);
}
