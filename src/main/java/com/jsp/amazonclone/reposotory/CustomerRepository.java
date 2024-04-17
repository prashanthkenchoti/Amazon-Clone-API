package com.jsp.amazonclone.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
