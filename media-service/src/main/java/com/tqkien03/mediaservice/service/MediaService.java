package com.tqkien03.mediaservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tqkien03.mediaservice.dto.FileSupport;
import com.tqkien03.mediaservice.exception.ResourceNotFoundException;
import com.tqkien03.mediaservice.media.MediaMapper;
import com.tqkien03.smcommon.dto.MediaDto;
import com.tqkien03.smcommon.model.Media;
import com.tqkien03.smcommon.model.User;
import com.tqkien03.smcommon.repository.MediaRepository;
import com.tqkien03.smcommon.repository.UserRepository;
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
public class MediaService {
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    public MediaDto uploadToCloudinary(MultipartFile file, String userId) {
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
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
            Media media = Media.builder().url(fileUrl).mediaName(fileName)
                    .mediaType(file.getContentType())
                    .width(width).height(height).size(file.getSize())
                    .owner(user).build();
            mediaRepository.save(media);
            return mediaMapper.toMediaDto(media);
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

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
