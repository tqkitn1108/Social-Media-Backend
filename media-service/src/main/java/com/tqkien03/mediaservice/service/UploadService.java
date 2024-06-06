package com.tqkien03.mediaservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tqkien03.mediaservice.dto.FileSupport;
import com.tqkien03.mediaservice.model.Media;
import com.tqkien03.mediaservice.repository.MediaRepository;
import lombok.AllArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UploadService {
    private final MediaRepository mediaRepository;
    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    public Media uploadToCloudinary(MultipartFile file, String userId) {
        try {
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
            File fileUp = convertMultipartToFile(file, fileName);
            String fileUrl = uploadFileToCloudinary(fileName, fileUp, file.getContentType());
            float height = 0;
            float width = 0;
            if (FileSupport.IMAGE.getTypes().contains(Objects.requireNonNull(file.getContentType()))) {
                BufferedImage image = ImageIO.read(file.getInputStream());
                height = image.getHeight();
                width = image.getWidth();
            }
            Media media = Media.builder().url(fileUrl).mediaName(fileName)
                    .mediaType(file.getContentType())
                    .width(width).height(height).size(file.getSize())
                    .ownerId(userId).build();
            return mediaRepository.save(media);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String uploadFileToCloudinary(String fileName, File fileUp, String mediaType) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
        try {
            String type = mediaType.contains("video") ? "video" : "image";
            Map response = cloudinary.uploader().upload(fileUp, ObjectUtils.asMap("resource_type", type));
            JSONObject json = new JSONObject(response);
            return json.getString("url");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
