package com.jsp.amazonclone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.amazonclone.entity.User;
import com.jsp.amazonclone.reposotory.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

	@Autowired
	private UserRepository  userRepository;


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("UserName Not Found"));
		return new CustomUserDetails(user);
	}
	
	

}
