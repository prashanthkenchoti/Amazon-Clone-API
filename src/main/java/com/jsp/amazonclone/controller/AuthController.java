package com.jsp.amazonclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.amazonclone.requestdto.AuthRequestDTO;
import com.jsp.amazonclone.requestdto.OtpModel;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.AuthResponseDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.ResponseStructure;
import com.jsp.amazonclone.utility.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")// enables the method to receive cross origin requests
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(@RequestBody UserRequestDTO userRequestDTO)
	{
		return authService.register(userRequestDTO);
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOTP( @RequestBody OtpModel otp)
	{
		System.out.println(otp.getOtp());
		return authService.verifyOTP(otp);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponseDTO>> login(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response)
	{
		return authService.login(authRequestDTO, response);
	}
	
	 @PreAuthorize(value = "hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PostMapping("/logout")
	public ResponseEntity<ResponseStructure<AuthResponseDTO>> logout(@CookieValue(name="at" ,required = false) String accessToken,@CookieValue(name="rt" ,required = false) String refreshToken,   HttpServletResponse response)
	{
		return authService.logout(accessToken,refreshToken,response);
	}
	
	 @PreAuthorize(value = "hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PostMapping("/revokeall")
	public ResponseEntity<SimpleResponseStructure> revokeAccessFromAllDevices()
	{
		return authService.revokeAccessFromAllDevices();
	}
	
	 @PreAuthorize(value = "hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PostMapping("/revokeother")
	public ResponseEntity<SimpleResponseStructure> revokeAccessFromOtherDevices(String accessToken,String refreshToken )
	{
		return authService.revokeAccessFromOtherDevices(accessToken,refreshToken);
	}
	 @PostMapping("/refreshlogin")
	 public ResponseEntity<SimpleResponseStructure> refreshLogin(@CookieValue String accessToken,@CookieValue String refreshToken, HttpServletResponse httpresponse )
		{
			return authService.refreshLogin(accessToken,refreshToken,httpresponse);
		}
	 
	 
	
}
