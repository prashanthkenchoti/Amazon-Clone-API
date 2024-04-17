package com.jsp.amazonclone.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.entity.Product;
import com.jsp.amazonclone.reposotory.ProductRepository;
import com.jsp.amazonclone.requestdto.ProductRequestDTO;
import com.jsp.amazonclone.responsedto.ProductResponseDTO;
import com.jsp.amazonclone.responsedto.StoreResponseDTO;
import com.jsp.amazonclone.service.ProductService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	private ResponseStructure<ProductResponseDTO> responseStructure;
	

	@Override
	public ResponseEntity<ResponseStructure<ProductResponseDTO>> addProducts(ProductRequestDTO productRequestDTO) {
		Product product=productRepository.save(mapToProduct(productRequestDTO));
		
		return new  ResponseEntity<ResponseStructure<ProductResponseDTO>>(responseStructure.setStatusCode(HttpStatus.ACCEPTED.value())
				.setMessage("Store details saved successfully ")
				.setData(mapToProductResponseDTO(product)),HttpStatus.ACCEPTED);
	}
	
	//====================================PRIVATE METHODS============================================
	
	private Product mapToProduct(ProductRequestDTO productRequestDTO)
	{
		return Product.builder()
				.productName(productRequestDTO.getProductName())
				.productDescription(productRequestDTO.getProductDescription())
				.productQuantity(productRequestDTO.getProductQuantity())
				.productPrice(productRequestDTO.getProductPrice())
				.availabilityStatus(productRequestDTO.getAvailabilityStatus())
				.build();
	}
	
	private ProductResponseDTO mapToProductResponseDTO(Product product)
	{
		return ProductResponseDTO.builder()
				.productName(product.getProductName())
				.productDescription(product.getProductDescription())
				.productQuantity(product.getProductQuantity())
				.productPrice(product.getProductPrice())
				.availabilityStatus(product.getAvailabilityStatus())
				.build();
	}

}
