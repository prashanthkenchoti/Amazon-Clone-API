package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.amazonclone.utility.ResponseStructure;

public interface ImageService {

	ResponseEntity<ResponseStructure<String>> addStoreImage(int storeId, MultipartFile image);

	ResponseEntity<byte[]> findStoreImage(String imageId);

	ResponseEntity<ResponseStructure<String>> addProductImage(int prductId, MultipartFile image);

	ResponseEntity<byte[]> findProductImage(String imageId);

}
