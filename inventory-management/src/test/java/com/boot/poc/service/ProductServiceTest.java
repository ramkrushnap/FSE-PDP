package com.boot.poc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.boot.poc.products.exception.ProductNotFoundException;
import com.boot.poc.products.model.Product;
import com.boot.poc.products.model.ProductNew;
import com.boot.poc.products.repository.NewProductRepository;
import com.boot.poc.products.repository.ProductRepository;
import com.boot.poc.products.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	NewProductRepository newProductRepository;

	@InjectMocks
	ProductService productService;

	@Test
	public void findAllProductTest() {

		List<Product> mockProduct = Arrays.asList(new Product(2l, "lapi", "hp", "123445"),
				new Product(3l, "desktop", "lenovo", "len2020"));

		when(productRepository.findAll()).thenReturn(mockProduct);

		List<Product> actualProducts = productService.findAllProduct();

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test
	public void findAllNewProductTest() {

		List<ProductNew> mockProduct = Arrays.asList(new ProductNew(2l, "lapi", "hp", "steve"),
				new ProductNew(3l, "desktop", "lenovo", "steve1"));

		when(newProductRepository.findAll()).thenReturn(mockProduct);

		List<ProductNew> actualProducts = productService.findAllNewProduct();

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test
	public void findByIdTest() {

		Product mockProduct = new Product(2l, "lapi", "hp", "8976");

		when(productRepository.findById(2L)).thenReturn(Optional.of(mockProduct));

		Product actualProducts = productService.findById(2L);

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test(expected = ProductNotFoundException.class)
	public void findById_Exception_Test() {
		productService.findById(5L);
	}

	@Test
	public void findNewProductByIdTest() {

		ProductNew mockProduct = new ProductNew(2L, "lapi", "hp", "steve");

		when(newProductRepository.findById(2L)).thenReturn(Optional.of(mockProduct));

		ProductNew actualProducts = productService.findNewProductById(2L);

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test(expected = ProductNotFoundException.class)
	public void findNewProductById_Exception_Test() {
		productService.findNewProductById(5L);
	}

	@Test
	public void addProductTest() {

		Product mockProduct = new Product(2l, "lapi", "hp", "8976");

		when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

		Product actualProducts = productService.addProduct(mockProduct);

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test
	public void addNewProductTest() {

		ProductNew mockProduct = new ProductNew(2L, "lapi", "hp", "steve");

		when(newProductRepository.save(any(ProductNew.class))).thenReturn(mockProduct);

		ProductNew actualProducts = productService.addNewProduct(mockProduct);

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test
	public void updateByIdTest() {

		Product mockProduct = new Product(2l, "lapi", "hp", "8976");

		when(productRepository.findById(2L)).thenReturn(Optional.of(mockProduct));

		when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

		Product actualProducts = productService.updateById(2L, mockProduct);

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test
	public void updateById_Null_Test() {

		Product mockProduct = new Product(2l, "lapi", "hp", "8976");

		Product actualProducts = productService.updateById(2L, mockProduct);

		assertThat(actualProducts).isNull();
	}

	@Test
	public void updateNewProductByIdTest() {

		ProductNew mockProduct = new ProductNew(2L, "lapi", "hp", "steve");

		when(newProductRepository.findById(2L)).thenReturn(Optional.of(mockProduct));

		when(newProductRepository.save(any(ProductNew.class))).thenReturn(mockProduct);

		ProductNew actualProducts = productService.updateNewProductById(2L, mockProduct);

		assertThat(actualProducts).isEqualTo(mockProduct);
	}

	@Test
	public void updateNewProductById_Null_Test() {

		ProductNew mockProduct = new ProductNew(2l, "lapi", "hp", "8976");

		ProductNew actualProducts = productService.updateNewProductById(2L, mockProduct);

		assertThat(actualProducts).isNull();
	}

	@Test
	public void deleteByIdTest() {

		Product mockProduct = new Product(2l, "lapi", "hp", "8976");
		Product mockProduct1 = new Product(3l, "desktop", "lenovo", "len2020");

		when(productRepository.findById(2L)).thenReturn(Optional.of(mockProduct1));

		Product actualProducts = productService.deleteById(2L);

		assertThat(actualProducts).isEqualTo(mockProduct1);
	}
	
	@Test
	public void deleteById_Null_Test() {

		Product actualProducts = productService.deleteById(2L);

		assertThat(actualProducts).isNull();
	}

	@Test
	public void deleteNewProductByIdTest() {

		ProductNew mockProduct = new ProductNew(2l, "lapi", "hp", "steve");
		ProductNew mockProduct1 = new ProductNew(3l, "desktop", "lenovo", "steve1");

		when(newProductRepository.findById(2L)).thenReturn(Optional.of(mockProduct1));

		ProductNew actualProducts = productService.deleteNewProductById(2L);

		assertThat(actualProducts).isEqualTo(mockProduct1);
	}
	
	@Test
	public void deleteNewProductById_Null_Test() {

		ProductNew actualProducts = productService.deleteNewProductById(2L);

		assertThat(actualProducts).isNull();
	}
	
}
