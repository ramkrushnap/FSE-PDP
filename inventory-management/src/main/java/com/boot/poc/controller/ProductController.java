package com.boot.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.boot.poc.exception.ProductNotFoundException;
import com.boot.poc.model.Product;
import com.boot.poc.service.ProductService;

@RestController
@RequestMapping("/inventory/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<Product> fetchAllProduct(){
		System.out.println("controler");
		return productService.findAllProduct();
	}
	
	@GetMapping("/products/{id}")
	public Product fetchProductById(@PathVariable long id){
		return  productService.findById(id);
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> initializeProduct(@RequestBody Product product) {
		try {
			Product productResponse = productService.InitializeUpdate(product);
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
		try {
			Product upadateProduct = productService.updateById(id, product);
			if (null == upadateProduct) {
				throw new ProductNotFoundException(id);
			} else {
				return new ResponseEntity<>(upadateProduct, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
		try {
			Product productResponse = productService.deleteById(id);
			if (null == productResponse) {
				throw new ProductNotFoundException(id);
			} else {
				return ResponseEntity.ok().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
