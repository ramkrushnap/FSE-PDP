package com.boot.poc.products.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="type")
	private String type;

	@Column(name="name", length = 20, nullable = false)
	private String name;

	@Column(name="code", nullable = false)
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Product() {
	}

	public Product(Long id, String type, String name, String code) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.code = code;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", type=" + type + ", name=" + name + ", code=" + code + "]";
	}



}
