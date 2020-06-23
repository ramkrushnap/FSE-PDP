package com.boot.poc.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.poc.products.model.ProductNew;

public interface NewProductRepository extends JpaRepository<ProductNew, Long>{

}
