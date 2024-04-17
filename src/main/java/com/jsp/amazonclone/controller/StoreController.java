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

import com.jsp.amazonclone.requestdto.StoreRequestDTO;
import com.jsp.amazonclone.responsedto.StoreResponseDTO;
import com.jsp.amazonclone.service.StoreService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5175/")
public class StoreController {
	
	private StoreService storeService;
	
	@PostMapping("/stores")
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> addStore(@RequestBody StoreRequestDTO storeRequestDTO)
	{
		return storeService.addStore(storeRequestDTO);
	}
	
	@GetMapping("/stores/{storeId}")
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> findStoreById(@PathVariable int storeId)
	{
		return storeService.findStoreById(storeId);
	}
	
	@GetMapping("/Seller/{SellerId}/stores")
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> findStoreBySeller(@PathVariable int SellerId)
	{
		return storeService.findStoreBySeller(SellerId);
	}

	
	@PutMapping("/Stores/{StoreId}")
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> updateStore(@RequestBody StoreRequestDTO storeRequestDTO,@PathVariable int StoreId)
	{
		return storeService.updateStore(storeRequestDTO,StoreId);
	}

	
	


}
