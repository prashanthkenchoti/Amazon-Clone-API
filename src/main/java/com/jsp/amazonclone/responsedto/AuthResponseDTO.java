package com.jsp.amazonclone.responsedto;

import java.time.LocalDateTime;

import com.jsp.amazonclone.Enum.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// we have to apply field validation to these variables
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
	
	private int userId;
	private String userName;
	private String role;
	private boolean isAuthenticated;
	private LocalDateTime accessExpiration;
	private LocalDateTime refreshExpiration;

}
