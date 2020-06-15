package com.boot.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.poc.controller.service.ProductService;
import com.boot.poc.model.Product;

@RestController
@RequestMapping("/inventory/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<Product> fetchAllProduct(){
		System.out.println("all cont");
		return productService.findAllProduct();
	}
	
	@GetMapping("/products/{id}")
	public Product fetchProductById(@PathVariable long id){
		System.out.println("cont");
		return  productService.findById(id);
	}

	
	@PostMapping("/products")
	public ResponseEntity<Product> initializeProduct(@RequestBody Product product) {
		return productService.InitializeUpdate(product);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
		return productService.updateById(id, product);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
		return productService.deleteById(id);
	}

}
