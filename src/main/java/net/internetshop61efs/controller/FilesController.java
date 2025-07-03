package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FilesController {

    private final FileService fileService;

    @PostMapping("/api/files")
    public String upload(@RequestParam("uploadFile")MultipartFile file) throws IOException {
        return fileService.upload(file);
    }
}
