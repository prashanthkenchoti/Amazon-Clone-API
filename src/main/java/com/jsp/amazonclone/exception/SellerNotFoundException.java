package com.jsp.amazonclone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerNotFoundException extends RuntimeException {
	
	private String message;

}
