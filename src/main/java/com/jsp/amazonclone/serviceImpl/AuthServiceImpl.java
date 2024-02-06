package com.jsp.amazonclone.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.entity.User;
import com.jsp.amazonclone.reposotory.CustomerRepository;
import com.jsp.amazonclone.reposotory.SellerRepository;
import com.jsp.amazonclone.reposotory.UserRepository;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.ResponseStructure;

public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("unchecked")
	public <T extends User> T  mapToUser(UserRequestDTO userRequestDTO)
	{
		return (T) User.builder()
				.email(userRequestDTO.getEmail())
				.password(userRequestDTO.getPassword())
				.userRole(userRequestDTO.getUserRole())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(UserRequestDTO userRequestDTO) {
		
		
		
		
		return null;
	}
	
	

}
