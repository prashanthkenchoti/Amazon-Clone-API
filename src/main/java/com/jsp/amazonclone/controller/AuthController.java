package com.jsp.amazonclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.ResponseStructure;

@RestController
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(@RequestBody UserRequestDTO userRequestDTO)
	{
		return authService.registerUser(userRequestDTO);
	}

}
