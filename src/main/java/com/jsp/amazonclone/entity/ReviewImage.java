package com.jsp.amazonclone.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewImage extends Image {
	
	 int reviewId;

}
