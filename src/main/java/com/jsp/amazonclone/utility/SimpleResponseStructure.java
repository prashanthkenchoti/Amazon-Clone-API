package com.jsp.amazonclone.utility;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponseStructure {
	
	private int statusCode;
	private String message;

}
