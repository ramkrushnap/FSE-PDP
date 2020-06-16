package com.boot.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.poc.model.ProductNew;

public interface NewProductRepository extends JpaRepository<ProductNew, Long>{

}
