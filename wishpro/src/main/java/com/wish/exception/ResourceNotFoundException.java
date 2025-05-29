package com.wish.exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	String fieldName;
	Long fieldValueId;
	String fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValueId) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValueId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValueId = fieldValueId;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
