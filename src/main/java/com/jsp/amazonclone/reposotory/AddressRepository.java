package com.jsp.amazonclone.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	
	

}
