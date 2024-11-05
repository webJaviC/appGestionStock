package com.printmanagement.service;

import com.printmanagement.dto.FileUploadDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileUploadDTO saveFile(MultipartFile file) {
        FileUploadDTO response = new FileUploadDTO();
        response.setFileName(file.getOriginalFilename());
        response.setFileType(file.getContentType());

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            response.setUploadStatus("success");
            response.setMessage("File uploaded successfully");
            return response;

        } catch (IOException e) {
            response.setUploadStatus("error");
            response.setMessage("Failed to upload file: " + e.getMessage());
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Path getFilePath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName);
    }
}