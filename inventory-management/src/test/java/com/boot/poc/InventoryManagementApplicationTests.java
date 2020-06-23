package com.boot.poc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.boot.poc.products.controller.ProductController;

@SpringBootTest
class InventoryManagementApplicationTests {

	@Autowired
	ProductController productController;
	
	@Test
	void contextLoads() {
		assertThat(productController).isNotNull();
	}

}
