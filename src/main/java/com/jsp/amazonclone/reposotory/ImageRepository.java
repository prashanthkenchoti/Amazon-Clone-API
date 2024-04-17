package com.jsp.amazonclone.reposotory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jsp.amazonclone.entity.Image;

public interface ImageRepository extends MongoRepository<Image, String> {

}
