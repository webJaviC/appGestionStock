package com.printmanagement.dto;

import lombok.Data;

@Data
public class FileUploadDTO {
    private String fileName;
    private String fileType;
    private String uploadStatus;
    private String message;
}