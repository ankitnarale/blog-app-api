package com.ram.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

//	String resourceName;
//	String fieldName;
//	long fieldValue;

//	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
//		
//		
//		super(resourceName +"with"+ fieldName+" : "+fieldValue+" not found ");
//
//		
////		super(String.format("%s with %s : %s not found ", resourceName, fieldName, fieldValue));
//		this.resourceName = resourceName;
//		this.fieldName = fieldName;
//		this.fieldValue = fieldValue;
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;
	Integer id;
	public ResourceNotFoundException(String msg, Integer id) {
		super(id+ " "+msg);
		this.msg = msg;
		this.id = id;
	}

	

}
