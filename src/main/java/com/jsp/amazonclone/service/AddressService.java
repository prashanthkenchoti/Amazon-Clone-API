package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.requestdto.AddressRequestDTO;
import com.jsp.amazonclone.responsedto.AddressResponseDTO;
import com.jsp.amazonclone.utility.ResponseStructure;

public interface AddressService {

	ResponseEntity<ResponseStructure<AddressResponseDTO>> addAddress(AddressRequestDTO addressRequestDTO);

	ResponseEntity<ResponseStructure<AddressResponseDTO>> findAddressById(int addressId);

	ResponseEntity<ResponseStructure<AddressResponseDTO>> updateAddress(AddressRequestDTO addressRequestDTO,
			int addressId);

	ResponseEntity<ResponseStructure<AddressResponseDTO>> findAddressByStore(int storeId);

}
