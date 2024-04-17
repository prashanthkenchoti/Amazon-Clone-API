package com.jsp.amazonclone.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestDTO {
	
	private String storeName;
	private String logoLink;
	private String about;

}
