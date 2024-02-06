package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.utility.ResponseStructure;

public interface AuthService {

	ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(UserRequestDTO userRequestDTO);

}
