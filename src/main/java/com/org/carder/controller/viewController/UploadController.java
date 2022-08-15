package com.org.carder.controller.viewController;


import com.org.carder.service.UploadService;
import com.org.carder.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {

    private final UploadService uploadService;
    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }


    @PostMapping("/upload")
    public CommonResponse singleFileUpload(@RequestParam("file") MultipartFile file) {
       return uploadService.uploadFile(file);
    }

}