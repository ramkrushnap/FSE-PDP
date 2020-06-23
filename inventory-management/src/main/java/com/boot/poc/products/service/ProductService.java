package com.boot.poc.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.boot.poc.products.exception.ProductNotFoundException;
import com.boot.poc.products.model.Product;
import com.boot.poc.products.model.ProductNew;
import com.boot.poc.products.repository.NewProductRepository;
import com.boot.poc.products.repository.ProductRepository;

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

	public Product addProduct(Product prod) {
			return productRepository.save(prod);
	}
	
	public ProductNew addNewProduct(ProductNew prod) {
		return newProductRepository.save(prod);
}
	
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
