package com.boot.poc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.boot.poc.controller.service.ProductService;
import com.boot.poc.model.Product;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	ProductController productController;

	@MockBean
	private ProductService mockService;

	@Test
	public void findAllTest() throws Exception {

		List<Product> mockProduct = Arrays.asList(new Product(2l, "lapi", "hp", "123445"));

		when(mockService.findAllProduct()).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/api/products")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":2,\"type\":\"lapi\",\"name\":\"hp\",\"code\":\"123445\"}]"))
				.andReturn();
	}

	@Test
	public void findByIdTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "123445");

		when(mockService.findById(2)).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/api/products/2")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.name", is("hp")));
	}

	@Test
	public void findAllPostTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "123445");

		when(mockService.InitializeUpdate(mockProduct)).thenReturn(new ResponseEntity<>(mockProduct, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/api/products")
				.accept(MediaType.APPLICATION_JSON).content(getArticleInJson()).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateProductTest() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/inventory/api/products/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getArticleInJson());

		this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void productDeleteTest() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/inventory/api/products/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String getArticleInJson() {
		return "{\"id\":2,\"type\":\"lapi\",\"name\":\"hp\",\"code\":\"123445\"}";
	}

}
