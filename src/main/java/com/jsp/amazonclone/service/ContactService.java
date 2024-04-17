package com.jsp.amazonclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.amazonclone.entity.Contact;
import com.jsp.amazonclone.requestdto.ContactRequestDTO;
import com.jsp.amazonclone.responsedto.ContactResponseDTO;
import com.jsp.amazonclone.utility.ResponseStructure;

public interface ContactService {

	ResponseEntity<ResponseStructure<ContactResponseDTO>> addContact(ContactRequestDTO contactRequestDTO);

	ResponseEntity<ResponseStructure<ContactResponseDTO>> findContactById(int contactId);

	ResponseEntity<ResponseStructure<ContactResponseDTO>> updateContactById(ContactRequestDTO contactRequestDTO, int contactId);

	ResponseEntity<ResponseStructure<Contact>> findContactByAddress(int addressId);

}
