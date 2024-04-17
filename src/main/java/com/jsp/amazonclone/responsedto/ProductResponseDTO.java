package com.jsp.amazonclone.responsedto;

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
public class ProductResponseDTO {
	
	private int productId;
	private String productName;
	private String productDescription;
	private long productPrice;
	private int productQuantity;
	private AvailabilityStatus availabilityStatus;
	private float averageRating;
	private int totalOrders;


}
