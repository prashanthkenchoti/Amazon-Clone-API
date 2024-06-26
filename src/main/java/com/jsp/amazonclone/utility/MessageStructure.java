package com.jsp.amazonclone.utility;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageStructure {
	
	private String to;
	private String Subject;
	private Date sentDate;
	private String text;

}
