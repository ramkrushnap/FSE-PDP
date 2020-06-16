package com.boot.poc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
		System.out.println("serv");
		return productRepository.findAll();
	}

	@Cacheable(value = "products", key="#id")
	public Product findById(long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}


	public Product InitializeUpdate(Product prod) {
			return productRepository.save(prod);
	}
	
	@SuppressWarnings("null")
	public Product updateById(long id, Product product){
		 Optional<Product> productData = productRepository.findById(id);

		    if (productData.isPresent()) {
		      Product _product = productData.get();
		      _product.setCode(product.getCode());
		      _product.setName(product.getName());
		      _product.setType(product.getType());
		      return productRepository.save(_product);
		    }
			return null; 
	}
	
	public Product deleteById(long id){
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return product.get();
		}
		return null; 
	}
}
