package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.requestdto.AuthRequestDTO;
import com.jsp.amazonclone.requestdto.OtpModel;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.AuthResponseDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.utility.ResponseStructure;
import com.jsp.amazonclone.utility.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	
	ResponseEntity<ResponseStructure<UserResponseDTO>> register(UserRequestDTO request);

	ResponseEntity<String> verifyOTP(OtpModel otpModel);

	ResponseEntity<ResponseStructure<AuthResponseDTO>> login(AuthRequestDTO authRequestDTO,HttpServletResponse response);

	//ResponseEntity<ResponseStructure<AuthResponseDTO>> logout(HttpServletRequest request, HttpServletResponse response);

	ResponseEntity<ResponseStructure<AuthResponseDTO>> logout(String accessToken, String refreshToken,
			HttpServletResponse response);

	ResponseEntity<SimpleResponseStructure> revokeAccessFromAllDevices();

	ResponseEntity<SimpleResponseStructure> revokeAccessFromOtherDevices(String accessToken,
			String refreshToken);

	ResponseEntity<SimpleResponseStructure> refreshLogin(String accessToken, String refreshToken, HttpServletResponse httpresponse);
}
