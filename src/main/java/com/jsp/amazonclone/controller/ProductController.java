package com.jsp.amazonclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.amazonclone.requestdto.ProductRequestDTO;
import com.jsp.amazonclone.responsedto.ProductResponseDTO;
import com.jsp.amazonclone.service.ProductService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {
	
	private ProductService productService;
	
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<ProductResponseDTO>> addProducts(@RequestBody ProductRequestDTO productRequestDTO )
	{
		return productService.addProducts(productRequestDTO);
	}

}
