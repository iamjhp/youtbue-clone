package com.example.jhp.service;

import com.example.jhp.model.Video;
import com.example.jhp.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public void uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);

        var video = new Video();
        video.setVideoUrl(videoUrl);

        videoRepository.save(video);
    }
}
