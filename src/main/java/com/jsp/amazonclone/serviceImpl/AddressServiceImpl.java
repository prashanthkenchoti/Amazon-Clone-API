package com.jsp.amazonclone.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.entity.Address;
import com.jsp.amazonclone.entity.Store;
import com.jsp.amazonclone.exception.AddressNotFoundException;
import com.jsp.amazonclone.exception.StoreNotFoundException;
import com.jsp.amazonclone.reposotory.AddressRepository;
import com.jsp.amazonclone.reposotory.StoreRepository;
import com.jsp.amazonclone.requestdto.AddressRequestDTO;
import com.jsp.amazonclone.responsedto.AddressResponseDTO;
import com.jsp.amazonclone.responsedto.StoreResponseDTO;
import com.jsp.amazonclone.service.AddressService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
	
	private AddressRepository addressRepository;
	private StoreRepository storeRepository;
	private ResponseStructure<AddressResponseDTO> responseStructure;
	
	
	@Override
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> addAddress(AddressRequestDTO addressRequestDTO) {
		
		Address address= addressRepository.save(mapToAddress(addressRequestDTO));
		
		return new  ResponseEntity<ResponseStructure<AddressResponseDTO>>(responseStructure.setStatusCode(HttpStatus.ACCEPTED.value())
				.setMessage("Store details saved successfully ")
				.setData(mapToAddresresponseDto(address)),HttpStatus.ACCEPTED);
		
	
	}
	
	@Override
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> findAddressById(int addressId) {
		Address address= addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address not found"));
		
		return new  ResponseEntity<ResponseStructure<AddressResponseDTO>>(responseStructure.setStatusCode(HttpStatus.FOUND.value())
				.setMessage("Store details saved successfully ")
				.setData(mapToAddresresponseDto(address)),HttpStatus.FOUND);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> findAddressByStore(int storeId) {
		Store store= storeRepository.findById(storeId).orElseThrow(() ->new StoreNotFoundException("Store not found"));
		Address address= addressRepository.findById(store.getStoreId()).orElseThrow(() -> new AddressNotFoundException("Address Not Found"));
		return new  ResponseEntity<ResponseStructure<AddressResponseDTO>>(responseStructure.setStatusCode(HttpStatus.FOUND.value())
				.setMessage("Store Address found successfully ")
				.setData(mapToAddresresponseDto(address)),HttpStatus.FOUND);
	}

	
	@Override
	public ResponseEntity<ResponseStructure<AddressResponseDTO>> updateAddress(AddressRequestDTO addressRequestDTO,
			int addressId) {
		Address address= addressRepository.findById(addressId).map(d ->{
			
			d.setStreetAddress(addressRequestDTO.getStreetAddress());
			d.setStreetAddressAdditional(addressRequestDTO.getStreetAddressAdditional());
			d.setCity(addressRequestDTO.getCity());
			d.setState(addressRequestDTO.getState());
			d.setCountry(addressRequestDTO.getCountry());
			d.setPincode(addressRequestDTO.getPincode());
			d.setAddressType(addressRequestDTO.getAddressType());
			return addressRepository.save(d);
			
		}).orElseThrow(() -> new AddressNotFoundException("Address Not Found"));
		
		return new  ResponseEntity<ResponseStructure<AddressResponseDTO>>(responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("Store details updated successfully ")
				.setData(mapToAddresresponseDto(address)),HttpStatus.OK);
	}
	
	
	
	//======================PRIVATE METHODS====================================
	
	private Address mapToAddress(AddressRequestDTO addressRequestDTO)
	{
		return Address.builder()
				.streetAddress(addressRequestDTO.getStreetAddress())
				.streetAddressAdditional(addressRequestDTO.getStreetAddressAdditional())
				.city(addressRequestDTO.getCity())
				.state(addressRequestDTO.getState())
				.country(addressRequestDTO.getCountry())
				.pincode(addressRequestDTO.getPincode())
				.addressType(addressRequestDTO.getAddressType())
				.build();
				
	}
	
	private AddressResponseDTO mapToAddresresponseDto(Address address)
	{
		return AddressResponseDTO.builder()
				.addressId(address.getAddressId())
				.streetAddress(address.getStreetAddress())
				.streetAddressAdditional(address.getStreetAddressAdditional())
				.city(address.getCity())
				.state(address.getState())
				.country(address.getCountry())
				.pincode(address.getPincode())
				.addressType(address.getAddressType())
				.build();
	}

	
	

	
}
