package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "File related operations")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload-image")
    @Operation(summary = "Upload an image file")
    public ResponseEntity<Response> uploadImage(@RequestParam("file") MultipartFile file) {
        Response response = fileService.saveFile(file);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/delete-image")
    @Operation(summary = "Delete an image file")
    public ResponseEntity<Response> deleteImage(@RequestParam("publicId") String publicId) {
        Response response = fileService.deleteFile(publicId);
        return new ResponseEntity<>(response, response.status());
    }

}
