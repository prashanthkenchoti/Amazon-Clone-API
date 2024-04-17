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

import com.jsp.amazonclone.entity.Contact;
import com.jsp.amazonclone.requestdto.AddressRequestDTO;
import com.jsp.amazonclone.requestdto.ContactRequestDTO;
import com.jsp.amazonclone.responsedto.AddressResponseDTO;
import com.jsp.amazonclone.responsedto.ContactResponseDTO;
import com.jsp.amazonclone.service.ContactService;
import com.jsp.amazonclone.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5175/")
public class ContactController {
	
	private ContactService contactService;
	
	@PostMapping("/contacts")
	public ResponseEntity<ResponseStructure<ContactResponseDTO>> addContact(@RequestBody ContactRequestDTO contactRequestDTO)
	{
		return contactService.addContact(contactRequestDTO);
	}
	
	@GetMapping("/contacts/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponseDTO>> findContactById(@PathVariable int contactId )
	{
		return contactService.findContactById(contactId);
	}
	
	@GetMapping("/addresses/{addressId}/contacts")
	public ResponseEntity<ResponseStructure<Contact>> findContactByAddress(@PathVariable int addressId)
	{
		return contactService.findContactByAddress(addressId);
	}
	
	@PutMapping("contacts/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponseDTO>> updateContactById(@RequestBody ContactRequestDTO contactRequestDTO, @PathVariable int contactId )
	{
		return contactService.updateContactById(contactRequestDTO,contactId);
	}
	
	

}
