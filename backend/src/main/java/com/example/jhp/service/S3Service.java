package com.example.jhp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService {
    public static final String YOUTUBE_CLONE_JHP = "youtube-clone-jhp";
    private final AmazonS3 amazonS3Client;

    public String uploadFile(MultipartFile file) {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var key = String.format("%s.%s", UUID.randomUUID(), extension);
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(YOUTUBE_CLONE_JHP, key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Exception occured while uploading the file");
        }
        amazonS3Client.setObjectAcl(YOUTUBE_CLONE_JHP, key, CannedAccessControlList.PublicRead);
        return amazonS3Client.getUrl(YOUTUBE_CLONE_JHP, key).toString();
    }
}
