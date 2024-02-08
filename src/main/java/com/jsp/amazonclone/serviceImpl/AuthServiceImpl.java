package com.jsp.amazonclone.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.entity.Customer;
import com.jsp.amazonclone.entity.Seller;
import com.jsp.amazonclone.entity.User;
import com.jsp.amazonclone.exception.UserNotFoundException;
import com.jsp.amazonclone.reposotory.CustomerRepository;
import com.jsp.amazonclone.reposotory.SellerRepository;
import com.jsp.amazonclone.reposotory.UserRepository;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private PasswordEncoder passwordEncoder;
	
	private CustomerRepository customerRepository;

	private SellerRepository sellerRepository;

	private UserRepository userRepository;

	private ResponseStructure<UserResponseDTO> responseStructure;

	// use of switch case statement with lambda expression(when using lambda expression in switchcase statements there is no need to write break statement)
	@SuppressWarnings("unchecked")
	public <T extends User> T mapToUser(UserRequestDTO userRequestDTO) {
		User user=null;
		switch (userRequestDTO.getUserRole()) {
		case CUSTOMER ->{
			user= new Customer();
		}     
		case SELLER ->{
			user= new Seller();
		}
		default-> throw new RuntimeException();
		}
		user.setEmail(userRequestDTO.getEmail()); 
	//	user.setPassword(userRequestDTO.getPassword());
		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		user.setUserName(user.getEmail().split("@")[0]);// autogenerating the username by splitting the email
		user.setUserRole(userRequestDTO.getUserRole());

		return (T) user;
	}

	private UserResponseDTO mapToUserResponseDTO(User user)
	{
		return UserResponseDTO.builder()
				.email(user.getEmail())
				.userName(user.getUserName())
				.userId(user.getUserId())
				.userRole(user.getUserRole())
				.build();
	}

	private User saveUser(User user)
	{
		switch (user.getUserRole()) {
		case CUSTOMER ->{
			user=customerRepository.save((Customer)user);
		}     
		case SELLER ->{
			user=sellerRepository.save((Seller)user);
		}
		default-> throw new RuntimeException();
		}
		return user;

	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(UserRequestDTO userRequestDTO) {
		User user= userRepository.findByUserName(userRequestDTO.getEmail().split("@")[0]).map(u ->{
			if(u.isEmailVerified())throw new UserNotFoundException("user already exists with the Specfied email id");
			
			else
			{
				//send an email to client with otp 
			}
			return u;
			
		}).orElse(saveUser(mapToUser(userRequestDTO)));
//		User user1=mapToUser(userRequestDTO);
//		user=saveUser(user1);
//		UserResponseDTO userResponseDTO = mapToUserResponseDTO(user);
		return new  ResponseEntity<ResponseStructure<UserResponseDTO>>(responseStructure.setStatusCode(HttpStatus.ACCEPTED.value())
				.setMessage("please verify your mail Id through the otp sent ")
				.setData(mapToUserResponseDTO(user)),HttpStatus.ACCEPTED);
	}



}
