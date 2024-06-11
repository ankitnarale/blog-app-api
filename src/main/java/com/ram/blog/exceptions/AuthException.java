package com.ram.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException  extends RuntimeException{

	String msg;
	
	public AuthException(String msg) {
		super();
		this.msg = msg;
		
	}
	
	
//	public AuthException() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	
	
}
