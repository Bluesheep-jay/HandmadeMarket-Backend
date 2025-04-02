package com.handmadeMarket.Cloudinary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/upload")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/image")
    public CompletableFuture<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("file: " + "=======================");
        return cloudinaryService.uploadFile(file);
    }

    @PostMapping("/video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(cloudinaryService.uploadVideo(file));
    }


}
