package com.trulydesignfirm.namaha.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trulydesignfirm.namaha.exception.FileHandlingException;
import com.trulydesignfirm.namaha.model.ImageFile;
import com.trulydesignfirm.namaha.repository.FileRepo;
import com.trulydesignfirm.namaha.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepo fileRepo;
    private final Cloudinary cloudinary;

    @Override
    public ImageFile saveFile(MultipartFile file) {
        try {
            var uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("resource_type", "auto")
            );
            ImageFile newFile = new ImageFile();
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileType(file.getContentType());
            newFile.setPublicId((String) uploadResult.get("public_id"));
            newFile.setFileUrl((String) uploadResult.get("secure_url"));
            newFile.setFileSize(file.getSize());
            return fileRepo.save(newFile);
        } catch (IOException e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

    @Override
    public String deleteFile(String fileName) {
        try {
            ImageFile file = fileRepo
                    .findByFileName(fileName)
                    .orElseThrow(() -> new ResolutionException("File not found."));
            cloudinary.uploader().destroy(file.getPublicId(), ObjectUtils.emptyMap());
            fileRepo.delete(file);
            return "File " + fileName + " deleted successfully from Cloudinary.";
        } catch (IOException e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

}
