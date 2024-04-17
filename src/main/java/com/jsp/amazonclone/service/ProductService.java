package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.requestdto.ProductRequestDTO;
import com.jsp.amazonclone.responsedto.ProductResponseDTO;
import com.jsp.amazonclone.utility.ResponseStructure;

public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponseDTO>> addProducts(ProductRequestDTO productRequestDTO);

}
