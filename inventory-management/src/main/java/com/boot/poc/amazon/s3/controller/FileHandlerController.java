package com.boot.poc.amazon.s3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boot.poc.amazon.s3.service.AmazonS3ClientService;

@RestController
public class FileHandlerController {

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;
	
    @PostMapping("/api/file/upload")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile file) {
    	String fileName = file.getOriginalFilename();
    	amazonS3ClientService.uploadFile(fileName, file);
		return "Upload Successfully -> FileName = " + fileName;
    }  
}