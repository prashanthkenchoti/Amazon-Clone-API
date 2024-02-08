package com.jsp.amazonclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.amazonclone.requestdto.OtpModel;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.ResponseStructure;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
	
	@Autowired
	AuthService authService;
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(@RequestBody UserRequestDTO userRequestDTO)
	{
		return authService.register(userRequestDTO);
	}

	@PostMapping("/varify-otp")
	public ResponseEntity<String> verifyOTP( @RequestBody OtpModel otp)
	{
		return authService.verifyOTP(otp);
	}
}
