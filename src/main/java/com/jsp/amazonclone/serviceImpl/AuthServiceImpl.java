package com.jsp.amazonclone.serviceImpl;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.cache.CacheStored;
import com.jsp.amazonclone.entity.Customer;
import com.jsp.amazonclone.entity.Seller;
import com.jsp.amazonclone.entity.User;
import com.jsp.amazonclone.reposotory.CustomerRepository;
import com.jsp.amazonclone.reposotory.SellerRepository;
import com.jsp.amazonclone.reposotory.UserRepository;
import com.jsp.amazonclone.requestdto.OtpModel;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.MessageStructure;
import com.jsp.amazonclone.utility.ResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private ResponseStructure<UserResponseDTO> responseStructure;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private SellerRepository sellerRepo;
	@Autowired
	private CacheStored<String> otpCacheStore;
	@Autowired
	private CacheStored<User> userCacheStore; 
	
	@Autowired
	private JavaMailSender javaMailSender;

	@SuppressWarnings("unchecked")
	private <T extends User> T mapToUserRequest(UserRequestDTO request) {
		User user = null;
		switch (request.getUserRole()) {
		case CUSTOMER -> {
			user = new Customer();
		}
		case SELLER -> {
			user = new Seller();
		}
		}
		user.setEmail(request.getEmail());
		user.setUserName(request.getEmail().split("@")[0]);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUserRole(request.getUserRole());
		return (T) user;
	}

	private UserResponseDTO mapToUserResponseDTO(User users) {
		return UserResponseDTO.builder().userId(users.getUserId()).userName(users.getUserName()).email(users.getEmail())
				.userRole(users.getUserRole()).build();
	}

	//math.ramdom
	private String generateOTP() {
		//math.ramdom
		return String.valueOf(new Random().nextInt(100000,999999));
	}

	private User saveUser(User user) {
		switch (user.getUserRole()) {
		case CUSTOMER -> {
			user = customerRepo.save((Customer) user);
		}
		case SELLER -> {
			user = sellerRepo.save((Seller) user);
		}
		default -> throw new RuntimeException();
		}
		return user;
	}



	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> register(UserRequestDTO request) {
		if (userRepo.existsByEmail(request.getEmail()))
			throw new RuntimeException("User does not exist by this email id");
		String OTP = generateOTP();//"45789";
		User user = mapToUserRequest(request);
		userCacheStore.add(request.getEmail(), user);
		otpCacheStore.add(request.getEmail(), OTP);
		
	
			try {
				sendotpToMail( user, OTP);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				log.error("This eamil address dosent Exist"+OTP);
				e.printStackTrace();
			}
	
		
		return new  ResponseEntity<ResponseStructure<UserResponseDTO>>(responseStructure.setStatusCode(HttpStatus.ACCEPTED.value())
				.setMessage("please verify your mail Id through the otp sent ")
				.setData(mapToUserResponseDTO(user)),HttpStatus.ACCEPTED);
	}


	@Override
	public ResponseEntity<String> verifyOTP(OtpModel otpModel) {
		User user = userCacheStore.get(otpModel.getEmail());
		String otp = otpCacheStore.get(otpModel.getEmail());

		if(otp==null) throw new RuntimeException("OTP expired");
		if(user==null)throw new RuntimeException("Registartion Session Expired");
		if(!otp.equals(otpModel.getOtp())) throw new RuntimeException("Invalid Exception");
		else {
		user.setEmailVerified(true);
		userRepo.save(user);
		}
		return new ResponseEntity<String>("registrarion Sucessfull",HttpStatus.CREATED);
	}
	
	private void sendotpToMail(User user,String otp) throws MessagingException
	{
		sendMail( MessageStructure.builder()
		.to(user.getEmail())
		.Subject("complete your registration to Amazon")
		.sentDate(new Date())
		.text(
				"hey,"+user.getUserName()
				+"Good to see you intrested in Amazon,"
				+"complete yoyr registration using OTP<br>"
				+"<h1>"+otp+"</h1><br>"
				+"Note: the OTP expires in one minute"
				+"<br><br>"
				+"with best Regards<br>"
				+"Amazon"
				
				).build());
		
		
	}
	
	@Async// annotation used to make method asynchronous it means to send send eah email simultaneously
	private void sendMail(MessageStructure message) throws MessagingException
	{
		MimeMessage mimeMessage= javaMailSender.createMimeMessage();
		MimeMessageHelper helper =new MimeMessageHelper(mimeMessage, true);
		helper.setTo(message.getTo());
		helper.setSubject(message.getSubject());
		helper.setSentDate(message.getSentDate());
		helper.setText(message.getText(),true);// true is used to enable html documents in the text
		javaMailSender.send(mimeMessage);
	}
}