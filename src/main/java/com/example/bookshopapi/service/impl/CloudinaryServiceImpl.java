package com.example.bookshopapi.service.impl;

import com.cloudinary.Cloudinary;
import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile, String folderName) {
        try {
            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("public_id", UUID.randomUUID().toString());
            uploadParams.put("folder", "bookshop_image/"+folderName);
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(), uploadParams)
                    .get("url")
                    .toString();
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
