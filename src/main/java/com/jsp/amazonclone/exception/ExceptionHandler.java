package com.jsp.amazonclone.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.net.HttpHeaders;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<ObjectError> allErrors=ex.getAllErrors();
		Map<String, String> errors= new HashMap<String,String>();
		allErrors.forEach(error ->{
			FieldError fieldError =(FieldError) error;
			errors.put(fieldError.getField(),fieldError.getDefaultMessage());
		});
		return responseStructure(HttpStatus.BAD_REQUEST,ex.getMessage(),errors );
		
	}
	
	private ResponseEntity<Object> responseStructure(HttpStatus status,String message,Object rootCause)
	{
		return new ResponseEntity<Object>(Map.of(
				"status",status.value(),
				"message",message,
				"rootcause",rootCause
				),status);
	}
	

	@org.springframework.web.bind.annotation.ExceptionHandler(UserNotLoggedInException.class)
	public ResponseEntity<Object> userNotLoggedIn(UserNotLoggedInException ex)
	{

		return responseStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "User not lgged in to application");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(StoreNotFoundException.class)
	public ResponseEntity<Object> storeNotFound(StoreNotFoundException storeNotFoundException)
	{

		return responseStructure(HttpStatus.NOT_FOUND, storeNotFoundException.getMessage(), "store not found with given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(SellerNotFoundException.class)
	public ResponseEntity<Object> sellerNotFound(SellerNotFoundException sellerNotFoundException)
	{

		return responseStructure(HttpStatus.NOT_FOUND, sellerNotFoundException.getMessage(), "seller not found with given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<Object> addressNotFound(AddressNotFoundException addressNotFoundException)
	{

		return responseStructure(HttpStatus.NOT_FOUND, addressNotFoundException.getMessage(), "Address not found with given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ContactNotFoundException.class)
	public ResponseEntity<Object> contactNotFound(ContactNotFoundException contactNotFoundException)
	{

		return responseStructure(HttpStatus.NOT_FOUND, contactNotFoundException.getMessage(), "Contact not found with given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<Object> imageNotFound(ImageNotFoundException imageNotFoundException)
	{

		return responseStructure(HttpStatus.NOT_FOUND, imageNotFoundException.getMessage(), "image not found with given Id");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> productNotFound(ProductNotFoundException productNotFoundException)
	{

		return responseStructure(HttpStatus.NOT_FOUND, productNotFoundException.getMessage(), "image not found with given Id");
	}
}
