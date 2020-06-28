package com.boot.poc.products.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.boot.poc.products.constants.ProductConstants;
import com.boot.poc.products.exception.ProductNotFoundException;
import com.boot.poc.products.model.Product;
import com.boot.poc.products.model.ProductNew;
import com.boot.poc.products.repository.NewProductRepository;
import com.boot.poc.products.repository.ProductRepository;

@Service
public class ProductService {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	NewProductRepository newProductRepository;

	@Cacheable(value = "products")
	public List<Product> findAllProduct() {
		logger.info(ProductConstants.SERVICE_GET_STARTED_V10);
		return productRepository.findAll();
	}
	
	@Cacheable(value = "products",key = "#root.methodName")
	public List<ProductNew> findAllNewProduct() {
		logger.info(ProductConstants.SERVICE_GET_STARTED_V11);
		return newProductRepository.findAll();
	}

	@Cacheable(value = "products", key="#id")
	public Product findById(long id) {
		logger.info(ProductConstants.SERVICE_GET_STARTED_BY_ID_V10);
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@Cacheable(value = "products", key="#id")
	public ProductNew findNewProductById(long id) {
		logger.info(ProductConstants.SERVICE_GET_STARTED_BY_ID_V10);
		return newProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	@CacheEvict(value= "products", allEntries= true)
	public Product addProduct(Product prod) {
			return productRepository.save(prod);
	}
	
	@CacheEvict(value= "products", allEntries= true)
	public ProductNew addNewProduct(ProductNew prod) {
		return newProductRepository.save(prod);
}
	
	@CacheEvict(value= "products", allEntries= true)
	public Product updateById(long id, Product product){
		 Optional<Product> productData = productRepository.findById(id);

		    if (productData.isPresent()) {
		      Product newProduct = productData.get();
		      newProduct.setCode(product.getCode());
		      newProduct.setName(product.getName());
		      newProduct.setType(product.getType());
		      return productRepository.save(newProduct);
		    }
			return null; 
	}
	
	@CacheEvict(value= "products", allEntries= true)
	public ProductNew updateNewProductById(long id, ProductNew product){
		 Optional<ProductNew> productData = newProductRepository.findById(id);

		    if (productData.isPresent()) {
		      ProductNew newProduct = productData.get();
		      newProduct.setType(product.getType());
		      newProduct.setProductName(product.getProductName());
		      newProduct.setProductHonour(product.getProductHonour());
		      return newProductRepository.save(newProduct);
		    }
			return null; 
	}
	
	@CacheEvict(value= "products", allEntries= true)
	public Product deleteById(long id){
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return product.get();
		}
		return null; 
	}
	
	@CacheEvict(value= "products", allEntries= true)
	public ProductNew deleteNewProductById(long id){
		Optional<ProductNew> newProduct = newProductRepository.findById(id);
		if (newProduct.isPresent()) {
			newProductRepository.deleteById(id);
			return newProduct.get();
		}
		return null; 
	}
}
