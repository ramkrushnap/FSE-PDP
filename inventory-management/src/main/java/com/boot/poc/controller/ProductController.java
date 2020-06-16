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
import com.boot.poc.model.ProductNew;
import com.boot.poc.service.ProductService;

@RestController
@RequestMapping("/inventory/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products/v1.0")
	public List<Product> fetchAllProduct(){
		return productService.findAllProduct();
	}
	
	@GetMapping("/products/v1.1")
	public List<ProductNew> fetchAllNewProduct(){
		return productService.findAllNewProduct();
	}
	
	@GetMapping("/products/v1.0/{id}")
	public Product fetchProductById(@PathVariable long id){
		return  productService.findById(id);
	}
	
	@GetMapping("/products/v1.1/{id}")
	public ProductNew fetchNewProductById(@PathVariable long id){
		return  productService.findNewProductById(id);
	}
	
	@PostMapping("/products/v1.0")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		try {
			Product productResponse = productService.InitializeUpdate(product);
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping("/products/v1.1")
	public ResponseEntity<ProductNew> addNewProduct(@RequestBody ProductNew product) {
		try {
			ProductNew productResponse = productService.addNewProduct(product);
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/products/v1.0/{id}")
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
	
	@PutMapping("/products/v1.1/{id}")
	public ResponseEntity<ProductNew> updateNewProduct(@PathVariable long id, @RequestBody ProductNew product) {
		try {
			ProductNew upadateProduct = productService.updateNewProductById(id, product);
			if (null == upadateProduct) {
				throw new ProductNotFoundException(id);
			} else {
				return new ResponseEntity<>(upadateProduct, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/v1.0/{id}")
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
	
	@DeleteMapping("/products/v1.1/{id}")
	public ResponseEntity<HttpStatus> deleteNewProductById(@PathVariable long id) {
		try {
			ProductNew productResponse = productService.deleteNewProductById(id);
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
