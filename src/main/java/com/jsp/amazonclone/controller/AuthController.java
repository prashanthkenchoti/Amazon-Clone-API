package com.jsp.amazonclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")// this is known as maintaining versions of our application
@AllArgsConstructor// it performs automatic constructor injection,
// autowiring performs field injection thus it is not a good practice hence always use constructor injection
public class AuthController {
	
	
	private AuthService authService;
	
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(@RequestBody UserRequestDTO userRequestDTO)
	{
		return authService.registerUser(userRequestDTO);
	}

}
