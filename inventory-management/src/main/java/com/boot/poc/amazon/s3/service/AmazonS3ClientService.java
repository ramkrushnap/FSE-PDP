package com.boot.poc.amazon.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService {
	
	public void uploadFile(String fileName, MultipartFile file);
}
