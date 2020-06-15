package com.boot.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
	private long id;
    public ProductNotFoundException(long id2) {
		super();
		this.id = id2;
	}
	public long getId() {
		return id;
	}

}
