package com.jsp.amazonclone.serviceImpl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.amazonclone.Enum.ImageType;
import com.jsp.amazonclone.entity.Image;
import com.jsp.amazonclone.entity.ProductImage;
import com.jsp.amazonclone.entity.Store;
import com.jsp.amazonclone.entity.StoreImage;
import com.jsp.amazonclone.exception.ImageNotFoundException;
import com.jsp.amazonclone.exception.ProductNotFoundException;
import com.jsp.amazonclone.exception.StoreNotFoundException;
import com.jsp.amazonclone.reposotory.ImageRepository;
import com.jsp.amazonclone.reposotory.ProductRepository;
import com.jsp.amazonclone.reposotory.StoreRepository;
import com.jsp.amazonclone.responsedto.AddressResponseDTO;
import com.jsp.amazonclone.service.ImageService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
	
	private ImageRepository imageRepository;
	private StoreRepository storeRepository;
	private ProductRepository productRepository;
	private ResponseStructure<String> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<String>> addStoreImage(int storeId, MultipartFile image) {
		
		StoreImage store= storeRepository.findById(storeId).map(s ->{
			StoreImage storeImage= new StoreImage();
			storeImage.setStoreId(storeId);
			storeImage.setImageType(ImageType.LOGO);
			try {
				storeImage.setImageBytes(image.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageRepository.save(storeImage);
			return storeImage;
			
			 
		}).orElseThrow(() -> new StoreNotFoundException("store not found"));
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		 responseStructure.setMessage("Store image saved ");
		 responseStructure.setData("/api/v1/images/"+store.getImageId());
		 return new  ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);			
					
	}

	@Override
	public ResponseEntity<byte[]> findStoreImage(String imageId) {
	return	imageRepository.findById(imageId).map(image ->{
		return	ResponseEntity.ok().contentType(MediaType.valueOf(image.getContentType()))
			.contentLength(image.getImageBytes().length)
			.body(image.getImageBytes());
			}).orElseThrow(() -> new ImageNotFoundException("image not found"));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> addProductImage(int prductId, MultipartFile image) {
		
	return	productRepository.findById(prductId).map(product ->{
			ProductImage pi= new ProductImage();
			pi.setProductId(prductId);
			pi.setImageType(ImageType.COVER);
			pi.setContentType(image.getContentType());
			try {
				pi.setImageBytes(image.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageRepository.save(pi);
			
			responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
			 responseStructure.setMessage("Product image saved ");
			 responseStructure.setData("/api/v1/images/"+pi.getImageId());
			 return new  ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);	
		}).orElseThrow(() -> new ProductNotFoundException("product image not found"));
		
		
	}

	@Override
	public ResponseEntity<byte[]> findProductImage(String imageId) {
		return	imageRepository.findById(imageId).map(image ->{
		return	ResponseEntity.ok().contentType(MediaType.valueOf(image.getContentType()))
				.contentLength(image.getImageBytes().length)
				.body(image.getImageBytes());
				}).orElseThrow(() -> new ImageNotFoundException("image not found"));
			
		
	}

}
