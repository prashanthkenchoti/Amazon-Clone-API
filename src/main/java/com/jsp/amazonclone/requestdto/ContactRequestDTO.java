package com.jsp.amazonclone.requestdto;

import com.jsp.amazonclone.Enum.Priority;

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

public class ContactRequestDTO {
	
	private String name;
	private Long contactNumber;
	private Priority priority;

}
