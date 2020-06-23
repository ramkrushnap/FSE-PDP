package com.boot.poc.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.poc.products.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
