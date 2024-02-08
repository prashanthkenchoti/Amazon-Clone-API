package com.jsp.amazonclone.utility;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseStructure<T> {
	
	private int statusCode;
	private String message;
	private T data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public ResponseStructure<T> setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public T getData() {
		return data;
	}
	public ResponseStructure<T> setData(T data) {
		this.data = data;
		return this;
	}
	
	
	
	

}
