package com.boot.poc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.boot.poc.products.controller.ProductController;
import com.boot.poc.products.model.Product;
import com.boot.poc.products.model.ProductNew;
import com.boot.poc.products.service.ProductService;

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

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/api/products/v1.0")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":2,\"type\":\"lapi\",\"name\":\"hp\",\"code\":\"123445\"}]"))
				.andReturn();
	}

	@Test
	public void findAll_Empty_Test() throws Exception {

		when(mockService.findAllProduct()).thenReturn(Collections.emptyList());
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/inventory/api/products/v1.0")).andReturn();
     	boolean response = result.getResponse().getContentAsString().isEmpty();
     	assertTrue(response);
	}
	
	@Test
	public void fetchAllNewProduct() throws Exception {

		List<ProductNew> mockProduct = Arrays.asList(new ProductNew(2l, "lapi", "hp", "steve"));

		when(mockService.findAllNewProduct()).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/api/products/v1.1")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content()
						.json("[{\"id\":2,\"type\":\"lapi\",\"productName\":\"hp\",\"productHonour\":\"steve\"}]"))
				.andReturn();
	}

	@Test
	public void findAllNewProduct_Empty_Test() throws Exception {
		when(mockService.findAllNewProduct()).thenReturn(Collections.emptyList());
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/inventory/api/products/v1.0")).andReturn();
     	boolean response = result.getResponse().getContentAsString().isEmpty();
     	assertTrue(response);
	}
	
	@Test
	public void findByIdTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "123445");

		when(mockService.findById(2)).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/api/products/v1.0/2")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.name", is("hp")));
	}

	@Test
	public void fetchNewProductById() throws Exception {

		ProductNew mockProduct = new ProductNew(2l, "lapi", "hp", "steve");

		when(mockService.findNewProductById(2)).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/api/products/v1.1/2")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.productName", is("hp")));
	}

	@Test
	public void findAllPostTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "123445");
		when(mockService.addProduct(mockProduct)).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/api/products/v1.0")
				.accept(MediaType.APPLICATION_JSON).content(getArticleInJson()).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void addProductTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "123445");
		when(mockService.addProduct(any(Product.class))).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/api/products/v1.0")
				.accept(MediaType.APPLICATION_JSON).content(getArticleInJson()).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void addNewProductTest() throws Exception {

		ProductNew mockProduct = new ProductNew(2l, "lapi", "hp", "steve");
		when(mockService.addNewProduct(any(ProductNew.class))).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/api/products/v1.1")
				.accept(MediaType.APPLICATION_JSON).content(getArticleInJson()).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void updateProductTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "hp2345");
		when(mockService.updateById(any(Long.class), any(Product.class))).thenReturn(mockProduct);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/inventory/api/products/v1.0/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(getArticleInJson()))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	@Test
	public void updateProduct_Null_Test() throws Exception {

		when(mockService.updateById(any(Long.class), any(Product.class))).thenReturn(null);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/inventory/api/products/v1.0/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(getArticleInJson()))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	@Test
	public void updateNewProductTest() throws Exception {

		ProductNew mockProduct = new ProductNew(2l, "lapi", "hp", "steve");
		when(mockService.updateNewProductById(any(Long.class), any(ProductNew.class))).thenReturn(mockProduct);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/inventory/api/products/v1.1/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(getArticleInJson()))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	@Test
	public void updateNewProduct_Null_Test() throws Exception {

		when(mockService.updateNewProductById(any(Long.class), any(ProductNew.class))).thenReturn(null);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/inventory/api/products/v1.1/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(getArticleInJson()))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	@Test
	public void deleteByIdTest() throws Exception {

		Product mockProduct = new Product(2l, "lapi", "hp", "hp4563");
		when(mockService.deleteById(any(Long.class))).thenReturn(mockProduct);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/inventory/api/products/v1.0/1")).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	@Test
	public void deleteById_Null_Test() throws Exception {

		when(mockService.deleteById(any(Long.class))).thenReturn(null);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/inventory/api/products/v1.0/1")).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), status);
	}

	@Test
	public void deleteNewProductById() throws Exception {

		ProductNew mockProduct = new ProductNew(2l, "lapi", "hp", "steve");
		when(mockService.deleteNewProductById(any(Long.class))).thenReturn(mockProduct);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/inventory/api/products/v1.1/1")).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	@Test
	public void deleteNewProductById_Null_Test() throws Exception {

		when(mockService.deleteNewProductById(any(Long.class))).thenReturn(null);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/inventory/api/products/v1.1/1")).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), status);
	}

	private String getArticleInJson() {
		return "{\"id\":2,\"type\":\"lapi\",\"name\":\"hp\",\"code\":\"123445\"}";
	}

}
