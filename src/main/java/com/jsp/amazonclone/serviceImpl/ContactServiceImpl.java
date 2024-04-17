package com.jsp.amazonclone.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.entity.Address;
import com.jsp.amazonclone.entity.Contact;
import com.jsp.amazonclone.exception.AddressNotFoundException;
import com.jsp.amazonclone.exception.ContactNotFoundException;
import com.jsp.amazonclone.reposotory.AddressRepository;
import com.jsp.amazonclone.reposotory.ContactRepository;
import com.jsp.amazonclone.requestdto.AddressRequestDTO;
import com.jsp.amazonclone.requestdto.ContactRequestDTO;
import com.jsp.amazonclone.responsedto.AddressResponseDTO;
import com.jsp.amazonclone.responsedto.ContactResponseDTO;
import com.jsp.amazonclone.service.ContactService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
	
	private ContactRepository contactRepository;
	private AddressRepository addressRepository;
	private ResponseStructure<ContactResponseDTO> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<ContactResponseDTO>> addContact(ContactRequestDTO contactRequestDTO) {
		Contact contact = contactRepository.save(mapToContact(contactRequestDTO));
		
		return new  ResponseEntity<ResponseStructure<ContactResponseDTO>>(responseStructure.setStatusCode(HttpStatus.ACCEPTED.value())
				.setMessage("Store details saved successfully ")
				.setData(mapToContactResponseDTO(contact)),HttpStatus.ACCEPTED);
		
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<ContactResponseDTO>> findContactById(int contactId) {
		Contact contact= contactRepository.findById(contactId).orElseThrow(() -> new ContactNotFoundException("contact not found"));
		
		return new  ResponseEntity<ResponseStructure<ContactResponseDTO>>(responseStructure.setStatusCode(HttpStatus.FOUND.value())
				.setMessage("contact details Found successfully ")
				.setData(mapToContactResponseDTO(contact)),HttpStatus.FOUND);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ContactResponseDTO>> updateContactById(ContactRequestDTO contactRequestDTO,int contactId) {
		Contact contact = contactRepository.findById(contactId).map(c ->{
			c.setName(contactRequestDTO.getName());
			c.setContactNumber(contactRequestDTO.getContactNumber());
			c.setPriority(contactRequestDTO.getPriority());
			return contactRepository.save(c);
		}).orElseThrow(() -> new ContactNotFoundException("contact details not found"));
		
		return new  ResponseEntity<ResponseStructure<ContactResponseDTO>>(responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("contact details updated successfully ")
				.setData(mapToContactResponseDTO(contact)),HttpStatus.OK);
		
	}
	
	@Override
	public ResponseEntity<ResponseStructure<Contact>> findContactByAddress(int addressId) {
		Address address= addressRepository.findById(addressId).orElseThrow(() -> new  AddressNotFoundException("Address Not Found"));
		//List<Contact> contact = contactRepository.findAll(address.getContact()).orElseThrow(() -> new  AddressNotFoundException("Address Not Found"));
		return null;
	}
	
	//=====================================PRIVATE METHODS==========================================

	private Contact mapToContact(ContactRequestDTO contactRequestDTO)
	{
		return Contact.builder()
				.name(contactRequestDTO.getName())
				.contactNumber(contactRequestDTO.getContactNumber())
				.priority(contactRequestDTO.getPriority())
				.build();
				
	}
	
	private ContactResponseDTO mapToContactResponseDTO(Contact contact)
	{
		return ContactResponseDTO.builder()
				.name(contact.getName())
				.contactNumber(contact.getContactNumber())
				.priority(contact.getPriority())
				.build();
	}


	


	

	

}
