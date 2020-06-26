package com.boot.poc.amazon.s3.controller;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.boot.poc.amazon.s3.service.AmazonS3ClientService;

@RunWith(SpringRunner.class)
@WebMvcTest(FileHandlerController.class)
public class FileHandlerControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	FileHandlerController fileHandlerController;

	@MockBean
	private AmazonS3ClientService amazonS3ClientService;
	
	@Test
	public void findAllPostTest() throws Exception {

		String fileName = "test.txt";
		MockMultipartFile multipartFile = new MockMultipartFile("file", fileName,
				"text/plain", "Spring Framework".getBytes());
		this.mockMvc.perform(multipart("/api/file/upload").file(multipartFile))
				.andExpect(status().isOk());

		then(this.amazonS3ClientService).should().uploadFile(fileName, multipartFile);
	}

}
