package com.jsp.amazonclone.responsedto;

import com.jsp.amazonclone.Enum.Priority;
import com.jsp.amazonclone.entity.Address;
import com.jsp.amazonclone.entity.Contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContactResponseDTO {

	private int contactId;
	private String name;
	private Long contactNumber;
	private Priority priority;
}
