package com.boot.poc.amazon.s3.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.boot.poc.amazon.s3.service.AmazonS3ClientService;

@Component
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService
{
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);
    
    @Autowired
	private AmazonS3 s3client;
 
	@Value("${aws.s3.bucket}")
	private String awsS3Bucket;
	
    @Override
	public void uploadFile(String fileName, MultipartFile file) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			s3client.putObject(awsS3Bucket, fileName, file.getInputStream(), metadata);
		} catch(IOException ioe) {
			logger.error(String.format("IOException is %s", ioe.getMessage()));
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info(String.format("Error Message:   %s", ase.getMessage()));
			logger.info(String.format("Error Type:     %s", ase.getErrorType()));
        } 
	}
	
}