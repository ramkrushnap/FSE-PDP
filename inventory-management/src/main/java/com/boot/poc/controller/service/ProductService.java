package com.boot.poc.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.boot.poc.exception.ProductNotFoundException;
import com.boot.poc.model.Product;
import com.boot.poc.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Cacheable(value = "products")
	public List<Product> findAllProduct() {
		System.out.println("service all");
		return productRepository.findAll();
	}

	@Cacheable(value = "products", key="#id")
	public Product findById(long id) {
		System.out.println("service");
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}


	public ResponseEntity<Product> InitializeUpdate(Product prod) {
		try {
			Product product = productRepository.save(prod);
			return new ResponseEntity<>(product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	public ResponseEntity<Product> updateById(long id, Product product){
		 Optional<Product> productData = productRepository.findById(id);

		    if (productData.isPresent()) {
		      Product _product = productData.get();
		      _product.setCode(product.getCode());
		      _product.setName(product.getName());
		      _product.setType(product.getType());
		      return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	}
	
	public ResponseEntity<HttpStatus> deleteById(long id){
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED); 
	}
}
