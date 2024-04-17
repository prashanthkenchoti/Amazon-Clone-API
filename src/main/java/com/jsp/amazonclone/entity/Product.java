package com.jsp.amazonclone.entity;

import com.jsp.amazonclone.Enum.AvailabilityStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private String productDescription;
	private long productPrice;
	private int productQuantity;
	private AvailabilityStatus availabilityStatus;
	private float averageRating;
	private int totalOrders;
	
	@ManyToOne
	private Store store; 

}
