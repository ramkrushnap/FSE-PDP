package com.boot.poc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.boot.poc.exception.ProductNotFoundException;
import com.boot.poc.model.Product;
import com.boot.poc.model.ProductNew;
import com.boot.poc.repository.NewProductRepository;
import com.boot.poc.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	NewProductRepository newProductRepository;

	@Cacheable(value = "products")
	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}
	
	@Cacheable(value = "newproducts")
	public List<ProductNew> findAllNewProduct() {
		return newProductRepository.findAll();
	}

	@Cacheable(value = "products", key="#id")
	public Product findById(long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@Cacheable(value = "newproduct", key="#id")
	public ProductNew findNewProductById(long id) {
		return newProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	public Product InitializeUpdate(Product prod) {
			return productRepository.save(prod);
	}
	
	public ProductNew addNewProduct(ProductNew prod) {
		return newProductRepository.save(prod);
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
	
	@SuppressWarnings("null")
	public ProductNew updateNewProductById(long id, ProductNew product){
		 Optional<ProductNew> productData = newProductRepository.findById(id);

		    if (productData.isPresent()) {
		      ProductNew _product = productData.get();
		      _product.setType(product.getType());
		      _product.setProductName(product.getProductName());
		      _product.setProductHonour(product.getProductHonour());
		      return newProductRepository.save(_product);
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
	
	public ProductNew deleteNewProductById(long id){
		Optional<ProductNew> newProduct = newProductRepository.findById(id);
		if (newProduct.isPresent()) {
			newProductRepository.deleteById(id);
			return newProduct.get();
		}
		return null; 
	}
}
