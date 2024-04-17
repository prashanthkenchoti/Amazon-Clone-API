package com.jsp.amazonclone.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.entity.Seller;
import com.jsp.amazonclone.entity.Store;
import com.jsp.amazonclone.exception.SellerNotFoundException;
import com.jsp.amazonclone.exception.StoreNotFoundException;
import com.jsp.amazonclone.reposotory.SellerRepository;
import com.jsp.amazonclone.reposotory.StoreRepository;
import com.jsp.amazonclone.requestdto.StoreRequestDTO;
import com.jsp.amazonclone.responsedto.StoreResponseDTO;
import com.jsp.amazonclone.responsedto.UserResponseDTO;
import com.jsp.amazonclone.service.StoreService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StroeServiceImpl implements StoreService {
	
	private StoreRepository storeRepository;
	private ResponseStructure<StoreResponseDTO> responseStructure;
	private SellerRepository sellerRepository;
	
	@Override
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> addStore(StoreRequestDTO storeRequestDTO) {
	Store store =	storeRepository.save(mapToStore(storeRequestDTO));
		
	return new  ResponseEntity<ResponseStructure<StoreResponseDTO>>(responseStructure.setStatusCode(HttpStatus.ACCEPTED.value())
			.setMessage("Store details saved successfully ")
			.setData(mapToStoreResponseDTO(store)),HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> findStoreById(int storeId) {
		Store store=storeRepository.findById(storeId).orElseThrow(() ->new StoreNotFoundException("store not found"));
		
		return new  ResponseEntity<ResponseStructure<StoreResponseDTO>>(responseStructure.setStatusCode(HttpStatus.FOUND.value())
				.setMessage("Store details found successfully ")
				.setData(mapToStoreResponseDTO(store)),HttpStatus.FOUND);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> findStoreBySeller(int sellerId) {
	Seller seller= sellerRepository.findById(sellerId).orElseThrow(() -> new SellerNotFoundException("seller not found"));
	Store store= storeRepository.findById(seller.getUserId()).orElseThrow(() -> new  StoreNotFoundException("store not found"));
	
	return new  ResponseEntity<ResponseStructure<StoreResponseDTO>>(responseStructure.setStatusCode(HttpStatus.OK.value())
			.setMessage("Store details found successfully ")
			.setData(mapToStoreResponseDTO(store)),HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<ResponseStructure<StoreResponseDTO>> updateStore(StoreRequestDTO storeRequestDTO,
			int storeId) {
		Store store=storeRepository.findById(storeId).map(s ->{
			s.setStoreName(storeRequestDTO.getStoreName());
			s.setAbout(storeRequestDTO.getAbout());
			return storeRepository.save(s);
		}).orElseThrow(() -> new StoreNotFoundException("Store not found"));
		
		return new  ResponseEntity<ResponseStructure<StoreResponseDTO>>(responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("Store details Updated successfully ")
				.setData(mapToStoreResponseDTO(store)),HttpStatus.OK);
		
	}
	
	//====================================PRIVATE METHODS======================================================
	
	private Store mapToStore(StoreRequestDTO storeRequestDTO )
	{
		return  Store.builder()
				.storeName(storeRequestDTO.getStoreName())
				.logoLink(storeRequestDTO.getLogoLink())
				.about(storeRequestDTO.getAbout())
				.build();
	}
	
	private StoreResponseDTO mapToStoreResponseDTO(Store store)
	{
		return StoreResponseDTO.builder()
				.storeId(store.getStoreId())
				.storeName(store.getStoreName())
				.logoLink(store.getLogoLink())
				.about(store.getAbout())
				.build();
	}
	

	
	

}
