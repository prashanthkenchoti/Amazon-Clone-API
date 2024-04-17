package com.jsp.amazonclone.responsedto;

import com.jsp.amazonclone.Enum.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
	
	private int userId;
	private String userName;
	private String email;
	private UserRole userRole;

}
