package com.jsp.amazonclone.serviceImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.cache.CacheStored;
import com.jsp.amazonclone.entity.AccessToken;
import com.jsp.amazonclone.entity.Customer;
import com.jsp.amazonclone.entity.RefreshToken;
import com.jsp.amazonclone.entity.Seller;
import com.jsp.amazonclone.entity.User;
import com.jsp.amazonclone.exception.UserNotLoggedInException;
import com.jsp.amazonclone.reposotory.AccessTokenRepository;
import com.jsp.amazonclone.reposotory.CustomerRepository;
import com.jsp.amazonclone.reposotory.RefreshTokenRepository;
import com.jsp.amazonclone.reposotory.SellerRepository;
import com.jsp.amazonclone.reposotory.UserRepository;
import com.jsp.amazonclone.requestdto.AuthRequestDTO;
import com.jsp.amazonclone.requestdto.OtpModel;
import com.jsp.amazonclone.requestdto.UserRequestDTO;
import com.jsp.amazonclone.responsedto.AuthResponseDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.security.JwtService;
import com.jsp.amazonclone.service.AuthService;
import com.jsp.amazonclone.utility.CookieManager;
import com.jsp.amazonclone.utility.MessageStructure;
import com.jsp.amazonclone.utility.ResponseStructure;
import com.jsp.amazonclone.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	PasswordEncoder passwordEncoder;

	private ResponseStructure<UserResponseDTO> responseStructure;

	private ResponseStructure<AuthResponseDTO> authStructure;

	private UserRepository userRepo;

	private CustomerRepository customerRepo;

	private SellerRepository sellerRepo;

	private CacheStored<String> otpCacheStore;

	private CacheStored<User> userCacheStore; 


	private JavaMailSender javaMailSender;


	private AuthenticationManager authenticationManager;

	private JwtService jwtService;


	private CookieManager cookieManager;

	private AccessTokenRepository accessTokenRepository;

	private RefreshTokenRepository refreshTokenRepository;

	@Value("${myapp.access.expiry}")
	private int accessExpiryInSeconds;

	@Value("${myapp.refresh.expiry}")
	private int refreshExpiryInSeconds;



	// GENERATING CONSTRUCTOR FOR FIELDS

	public AuthServiceImpl(PasswordEncoder passwordEncoder, 
			ResponseStructure<UserResponseDTO> responseStructure,
			ResponseStructure<AuthResponseDTO> authStructure,
			UserRepository userRepo,
			CustomerRepository customerRepo,
			SellerRepository sellerRepo,
			CacheStored<String> otpCacheStore,
			CacheStored<User> userCacheStore,
			JavaMailSender javaMailSender,
			AuthenticationManager authenticationManager,
			CookieManager cookieManager,
			JwtService jwtService,
			AccessTokenRepository accessTokenRepository,
			RefreshTokenRepository refreshTokenRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.responseStructure = responseStructure;
		this.authStructure=authStructure;
		this.userRepo = userRepo;
		this.customerRepo = customerRepo;
		this.sellerRepo = sellerRepo;
		this.otpCacheStore = otpCacheStore;
		this.userCacheStore = userCacheStore;
		this.javaMailSender = javaMailSender;
		this.authenticationManager = authenticationManager;
		this.cookieManager = cookieManager;
		this.jwtService=jwtService;
		this.accessTokenRepository=accessTokenRepository;
		this.refreshTokenRepository=refreshTokenRepository;
	}

	//============================================PUBLIC METHODS==============================================

	//METHOD TO REGISTER USER 
	@Override
	public ResponseEntity<ResponseStructure<UserResponseDTO>> register(UserRequestDTO request) {
		if (userRepo.existsByEmail(request.getEmail()))
			//throw new RuntimeException("User does not exist by this email id");
		
		System.out.println(" enterd ");
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

	//METHOD TO VERIFY OTP

	@Override
	public ResponseEntity<String> verifyOTP(OtpModel otpModel) {
		User user = userCacheStore.get(otpModel.getEmail());
		String otp = otpCacheStore.get(otpModel.getEmail());
		System.out.println(otp);
		if(otp==null) throw new RuntimeException("OTP expired");
		if(user==null)throw new RuntimeException("Registartion Session Expired");
		if(!otp.equals(otpModel.getOtp())) throw new RuntimeException("Invalid Exception");
		else {
			user.setEmailVerified(true);
			userRepo.save(user);
			try {
				sendWelComeMil( user);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new ResponseEntity<String>("registrarion Sucessfull",HttpStatus.CREATED);
	}

	//METHOD TO LOGIN USER

	@Override
	public ResponseEntity<ResponseStructure<AuthResponseDTO>> login(AuthRequestDTO authRequestDTO,HttpServletResponse response) {

		String userName= authRequestDTO.getEmail().split("@")[0];
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(userName,authRequestDTO.getPassword());
		org.springframework.security.core.Authentication authentication=authenticationManager.authenticate(token);
		if(!authentication.isAuthenticated())
		{
			throw new UsernameNotFoundException("failed authenticate the user");
		}
		else
		{
			//generating the cookies and returning to the client

			return	 userRepo.findByUserName(userName).map(user ->{
				grantAccess(response, user);
				return 	ResponseEntity.ok(authStructure.setStatusCode(HttpStatus.OK.value())
						.setData(AuthResponseDTO.builder()
								.userId(user.getUserId())
								.userName(userName)
								.role(user.getUserName())
								.isAuthenticated(true)
								.accessExpiration(LocalDateTime.now().plusSeconds(accessExpiryInSeconds))
								.refreshExpiration(LocalDateTime.now().plusSeconds(refreshExpiryInSeconds))
								.build()));

			}).get();

		}
	}

	// METHOD TO 
	@Override
	public ResponseEntity<ResponseStructure<AuthResponseDTO>> logout(String accessToken,String refreshToken,
			HttpServletResponse response) {
		if(accessToken==null && refreshToken==null )
		{
			throw new UserNotLoggedInException("user not Logged in to Application");
		}

		else
		{

			String at = null;
			String rt = null;
			// traditional way of accession and deketing cookies.			
			//			for (Cookie cookie : cookies) {
			//				if(cookie.getName().equals("at"))
			//				{
			//					at=cookie.getValue();
			//				}
			//				if(cookie.getName().equals("rt"))
			//				{
			//					rt=cookie.getValue();
			//				}
			//				
			//			}
			//			accessTokenRepository.findByToken(at).ifPresent(access ->{
			//				access.setBlocked(true);
			//				accessTokenRepository.save(access);
			//			});
			AccessToken access = accessTokenRepository.findByToken(accessToken);
			access.setBlocked(true);
			accessTokenRepository.save(access);

//			refreshTokenRepository.findByToken(rt).ifPresent(refresh ->{
//				refresh.setBlocked(true);
//				refreshTokenRepository.save(refresh);
//			});
			RefreshToken refresh = refreshTokenRepository.findByToken(refreshToken);
			refresh.setBlocked(true);
			refreshTokenRepository.save(refresh);
			
			response.addCookie(cookieManager.invalidate(new Cookie("at","")));
			response.addCookie(cookieManager.invalidate(new Cookie("rt","")));
		}
		authStructure.setStatusCode(HttpStatus.OK.value());
		authStructure.setMessage("User LoggedOut Successfully");
		authStructure.setData(null);

		return new	ResponseEntity<ResponseStructure<AuthResponseDTO>>(authStructure,HttpStatus.OK);

	}

	@Override
	public ResponseEntity<SimpleResponseStructure> revokeAccessFromAllDevices()
	{
		String username =	SecurityContextHolder.getContext().getAuthentication().getName();
		userRepo.findByUserName(username).ifPresent(user ->{
			blockAccessTokens(accessTokenRepository.findAllByUserAndIsBlocked(user,false));
			blockRefreshTokens(refreshTokenRepository.findByTokenAndIsBlocked(user,false));

		});

		SimpleResponseStructure  simpleResponseStructure= new SimpleResponseStructure();
		simpleResponseStructure.setStatusCode(HttpStatus.OK.value());
		simpleResponseStructure.setMessage(" successfully revoked Access for all devices");

		return new ResponseEntity<SimpleResponseStructure>(simpleResponseStructure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> revokeAccessFromOtherDevices(String accessToken,
			String refreshToken) {
		String username =	SecurityContextHolder.getContext().getAuthentication().getName();
		userRepo.findByUserName(username).ifPresent(user ->{
			if(accessToken!=null && refreshToken!=null)
			{
				blockAccessTokens(accessTokenRepository.findAllByUserAndIsBlockedAndTokenNot(user,false,accessToken));
				blockRefreshTokens(refreshTokenRepository.findAllByUserAndIsBlockedAndTokenNot(user,false,refreshToken));
			}	
		});

		SimpleResponseStructure  simpleResponseStructure= new SimpleResponseStructure();
		simpleResponseStructure.setStatusCode(HttpStatus.OK.value());
		simpleResponseStructure.setMessage(" successfully revoked Access for all devices");

		return new ResponseEntity<>(simpleResponseStructure,HttpStatus.OK);

	}

	@Override
	public ResponseEntity<SimpleResponseStructure> refreshLogin(String accessToken, String refreshToken,HttpServletResponse httpresponse) {
		String username= SecurityContextHolder.getContext().getAuthentication().getName();
		User user= userRepo.findByUserName(username).get();
		if(accessToken==null)
		{
			
			grantAccess(httpresponse, user);
		}
		else
		{
			AccessToken at= accessTokenRepository.findByToken(accessToken);
			at.setBlocked(true);
			accessTokenRepository.save(at);
		}
		if(refreshToken!=null)
		{
			RefreshToken rt= refreshTokenRepository.findByToken(refreshToken);
			rt.setBlocked(true);
			refreshTokenRepository.save(rt);
			grantAccess(httpresponse, user);
		}
		else
		{
			throw new UserNotLoggedInException("user need to logged in to the application,kindly login to the application");
		}

		SimpleResponseStructure simpleResponseStructure = new SimpleResponseStructure();
		simpleResponseStructure.setStatusCode(HttpStatus.OK.value());
		simpleResponseStructure.setMessage("refresh token generated successfully..");
		return new  ResponseEntity<SimpleResponseStructure>(simpleResponseStructure,HttpStatus.OK);

	}





	//============================================PRIVATE METHODS==============================================


	private void blockAccessTokens(List<AccessToken> accessTokens)
	{
		accessTokens.forEach(at ->{
			at.setBlocked(true);
			accessTokenRepository.save(at);
		});
	}

	private void blockRefreshTokens(List<RefreshToken> refreshTokens)
	{
		refreshTokens.forEach(rt ->{
			rt.setBlocked(true);
			refreshTokenRepository.save(rt);
		});
	}

	//METHOD TO CONVERT USERREQUASTDTO OBJECT TO APPROPRIATE USERS BASED ON USERROLE 

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

	//	METHOD TO CONVERT USER OBJECT INTO USERRESPONSEDTO OBJECT 

	private UserResponseDTO mapToUserResponseDTO(User users) {
		return UserResponseDTO.builder().userId(users.getUserId()).userName(users.getUserName()).email(users.getEmail())
				.userRole(users.getUserRole()).build();
	}

	// METHOD TO GENERATE OTP

	private String generateOTP() {

		return String.valueOf(new Random().nextInt(100000,999999));
	}


	// METHOD TO SEND OTP TO USER EMAILID

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

	//METHOD TO SEND WELCOME MAIL ONCE THE REGISTRATION IS SUCCESSFUL

	private void sendWelComeMil(User user) throws MessagingException
	{
		sendMail( MessageStructure.builder()
				.to(user.getEmail())
				.Subject("Successfully Registered with Amazon")
				.sentDate(new Date())
				.text(
						" WelCome to Amazon " +user.getUserName()
						+" Congratulations on successfully registering with Amazon!<br>"
						+ " We're thrilled to have you join our community of savvy shoppers."
						+" At Amazon, we're dedicated to providing you with an exceptional shopping experience."
						+ " Whether you're searching for the latest gadgets, "
						+ "trendy fashion, or everyday essentials, we've got you covered. "
						+ " With a vast selection of products, "
						+ "exclusive deals, and seamless shopping features,"
						+ " we're here to make your online shopping journey delightful and convenient.<br>"
						+"<br> <br><br>"
						+" with best Regards<br>"
						+"Amazon"

						).build());


	}

	// METHOD TO SEND EMAIL

	@Async// annotation used to make method asynchronous it means to send send multiple email simultaneously
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


	private void grantAccess(HttpServletResponse response, User user)
	{
		//generating access and refresh tokens

		String accessToken=jwtService.generateAccessToken(user.getUserName());
		String refreshToken= jwtService.generateAccessToken(user.getUserName());

		//adding access and refresh tokens cookies to the response

		response.addCookie(cookieManager.configure(new Cookie("at", accessToken), accessExpiryInSeconds));

		response.addCookie(cookieManager.configure(new Cookie("rt", refreshToken), refreshExpiryInSeconds));

		//saving the access and refresh cookie in to the database

		accessTokenRepository.save(AccessToken.builder()
				.token(accessToken)
				.isBlocked(false)
				.expiration(LocalDateTime.now().plusSeconds(accessExpiryInSeconds))
				.build());

		refreshTokenRepository.save(RefreshToken.builder()
				.token(refreshToken)
				.isBlocked(false)
				.expiration(LocalDateTime.now().plusSeconds(refreshExpiryInSeconds))
				.build());

	}











}