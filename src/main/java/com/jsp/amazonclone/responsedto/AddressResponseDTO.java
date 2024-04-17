package com.jsp.amazonclone.responsedto;

import com.jsp.amazonclone.Enum.AddressType;

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
public class AddressResponseDTO {
	
	private int addressId;
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private String country;
	private int pincode;
	private AddressType addressType;

}
