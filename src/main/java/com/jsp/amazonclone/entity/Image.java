package com.jsp.amazonclone.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.jsp.amazonclone.Enum.ImageType;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
/*
 *  while using mongo dependency @entity will be replaced by @document,
 * because in mongo we store data in the form of files/documents.
 * we use datatype for primary key column as String instead of int*/
@Document(collection = "image")
@Getter
@Setter
public class Image {
	/*
	 * here the @id annotation should be 
	 * imported from @org.springframework.data.annotation package */
	
	@org.springframework.data.annotation.Id
	private String imageId;
	private ImageType imageType;
	private byte[] imageBytes;
	private String contentType;

}
