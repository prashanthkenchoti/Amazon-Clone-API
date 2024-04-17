package com.jsp.amazonclone.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.amazonclone.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
