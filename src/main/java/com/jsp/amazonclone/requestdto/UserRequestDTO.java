package com.jsp.amazonclone.requestdto;

import com.jsp.amazonclone.Enum.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

	private String email;
	private String password;
	private UserRole userRole;
}
