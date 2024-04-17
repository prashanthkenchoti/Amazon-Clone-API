package com.jsp.amazonclone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Seller extends User {
	
	@OneToOne
	private Store store;

}
