package com.boot.poc.products.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="newproduct")
public class ProductNew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="type")
	private String type;

	@Column(name="productname", length = 20, nullable = false)
	private String productName;

	@Column(name="honourname", nullable = false)
	private String productHonour;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductHonour() {
		return productHonour;
	}

	public void setProductHonour(String productHonour) {
		this.productHonour = productHonour;
	}

	public ProductNew() {
		super();
	}

	public ProductNew(Long id, String type, String productName, String productHonour) {
		super();
		this.id = id;
		this.type = type;
		this.productName = productName;
		this.productHonour = productHonour;
	}

	@Override
	public String toString() {
		return "ProductNew [id=" + id + ", type=" + type + ", productName=" + productName + ", productHonour="
				+ productHonour + "]";
	}
}
