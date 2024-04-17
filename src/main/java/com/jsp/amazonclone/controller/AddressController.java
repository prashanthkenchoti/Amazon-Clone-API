package com.jsp.amazonclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.amazonclone.requestdto.AddressRequestDTO;
import com.jsp.amazonclone.requestdto.StoreRequestDTO;
import com.jsp.amazonclone.responsedto.AddressResponseDTO;
import com.jsp.amazonclone.responsedto.StoreResponseDTO;
import com.jsp.amazonclone.service.AddressService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5175/")
public class AddressController {
	
	private AddressService addressService;
	
	@PostMapping("/addresses")
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> addAddress(@RequestBody AddressRequestDTO addressRequestDTO)
	{
		return addressService.addAddress(addressRequestDTO);
	}
	
	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> findAddressById(@PathVariable int addressId )
	{
		return addressService.findAddressById(addressId);
	}
	
	@PutMapping("/addresses/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> updateAddress(@RequestBody AddressRequestDTO addressRequestDTO , @PathVariable int addressId)
	{
		return addressService.updateAddress(addressRequestDTO,addressId);
	}
	
	@GetMapping("/stores/{storeId}/addresses")
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> findAddressByStore( @PathVariable int storeId)
	{
		return addressService.findAddressByStore(storeId);
	}
	
	
	

}
