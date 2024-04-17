package com.jsp.amazonclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.amazonclone.service.ImageService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ImageController {
	
	private ImageService imageService;
	
	@PostMapping("/stores/{storeId}/images")
	public ResponseEntity<ResponseStructure<String>> addStoreImage(@PathVariable int storeId,MultipartFile image)
	{
		return imageService.addStoreImage(storeId,image);
	}
	
	@GetMapping("/images/{imageId}/store-image")
	public ResponseEntity<byte[]> findStoreImage(@PathVariable String imageId)
	{
		return imageService.findStoreImage(imageId);
	}
	
	@PostMapping("/products/{prductId}/images")
	public ResponseEntity<ResponseStructure<String>> addProductImage(@PathVariable int prductId,MultipartFile image)
	{
		return imageService.addProductImage(prductId,image);
	}
	
	@GetMapping("/images/{imageId}/product-image")
	public ResponseEntity<byte[]> findProductImage(@PathVariable String imageId)
	{
		return imageService.findProductImage(imageId);
	}

}
