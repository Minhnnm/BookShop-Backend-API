package com.example.bookshopapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile multipartFile, String folderName);
}
