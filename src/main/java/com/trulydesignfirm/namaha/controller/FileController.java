package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.exception.FileHandlingException;
import com.trulydesignfirm.namaha.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "File related operations")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload-image")
    @Operation(summary = "Upload an image file")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return fileService.saveFile(file).getFileUrl();
        } catch (IOException e) {
            throw new FileHandlingException(e.getMessage());
        }
    }

    @DeleteMapping("/delete-image")
    @Operation(summary = "Delete an image file")
    public String deleteImage(@RequestBody Map<String, String> data) {
        String fileName = data.get("fileName");
        try {
            return fileService.deleteFile(fileName);
        } catch (IOException e) {
            throw new FileHandlingException("Failed to delete the image: " + e.getMessage());
        }
    }

}
