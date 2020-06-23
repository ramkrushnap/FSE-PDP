package com.boot.poc.products.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.poc.products.constants.ProductConstants;
import com.boot.poc.products.exception.ProductNotFoundException;
import com.boot.poc.products.model.Product;
import com.boot.poc.products.model.ProductNew;
import com.boot.poc.products.service.ProductService;

@RestController
@RequestMapping("/inventory/api")
public class ProductController {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping("/products/v1.0")
	public List<Product> fetchAllProduct() {
		logger.info(ProductConstants.MESSAGE_GET_STARTED_V10);
		List<Product> products = productService.findAllProduct();
		if (CollectionUtils.isEmpty(products)) {
			throw new ProductNotFoundException(ProductConstants.PRODUCT_NOT_FOUND);
		}
		return products;
	}

	@GetMapping("/products/v1.1")
	public List<ProductNew> fetchAllNewProduct() {
		logger.info(ProductConstants.MESSAGE_GET_STARTED_V11);
		List<ProductNew> newProducts = productService.findAllNewProduct();
		if (CollectionUtils.isEmpty(newProducts)) {
			throw new ProductNotFoundException(ProductConstants.PRODUCT_NOT_FOUND);
		}
		return newProducts;
	}
	
	@GetMapping("/products/v1.0/{id}")
	public Product fetchProductById(@PathVariable long id) {
		logger.info(ProductConstants.MESSAGE_GET_STARTED_BY_ID_V10);
		return productService.findById(id);
	}

	@GetMapping("/products/v1.1/{id}")
	public ProductNew fetchNewProductById(@PathVariable long id) {
		logger.info(ProductConstants.MESSAGE_GET_STARTED_BY_ID_V11);
		return productService.findNewProductById(id);
	}

	@PostMapping("/products/v1.0")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		try {
			Product productResponse = productService.addProduct(product);
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/products/v1.1")
	public ResponseEntity<ProductNew> addNewProduct(@RequestBody ProductNew product) {
		try {
			ProductNew productResponse = productService.addNewProduct(product);
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
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
