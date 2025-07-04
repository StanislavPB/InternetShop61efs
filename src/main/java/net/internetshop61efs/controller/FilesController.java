package net.internetshop61efs.controller;

import lombok.RequiredArgsConstructor;
import net.internetshop61efs.dto.MessageResponseDto;
import net.internetshop61efs.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FilesController {

    private final FileService service;

    @PostMapping("/files")
    public MessageResponseDto upload(@RequestParam("uploadFile")MultipartFile file){
        return service.uploadDigitalOceanStorage(file);
    }
}
