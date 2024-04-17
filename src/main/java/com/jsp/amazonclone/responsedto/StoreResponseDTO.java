package com.jsp.amazonclone.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponseDTO {

	private int storeId;
	private String storeName;
	private String logoLink;
	private String about;

}
