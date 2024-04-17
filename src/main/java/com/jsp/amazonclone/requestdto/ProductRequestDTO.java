package com.jsp.amazonclone.requestdto;

import com.jsp.amazonclone.Enum.AvailabilityStatus;

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
public class ProductRequestDTO {
	
	private String productName;
	private String productDescription;
	private long productPrice;
	private int productQuantity;
	private AvailabilityStatus availabilityStatus;


}
