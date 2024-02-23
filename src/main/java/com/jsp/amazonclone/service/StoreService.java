package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.requestdto.StoreRequestDTO;
import com.jsp.amazonclone.responsedto.StoreResponseDTO;
import com.jsp.amazonclone.utility.ResponseStructure;

public interface StoreService {

	ResponseEntity<ResponseStructure<StoreResponseDTO>> addStore(StoreRequestDTO storeRequestDTO);

	ResponseEntity<ResponseStructure<StoreResponseDTO>> findStoreById(int storeId);
	
	ResponseEntity<ResponseStructure<StoreResponseDTO>> findStoreBySeller(int sellerId);

	ResponseEntity<ResponseStructure<StoreResponseDTO>> updateStore(StoreRequestDTO storeRequestDTO, int storeId);

	

}
