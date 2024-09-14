package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.quizapp.entity.User;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
		User user = userService.getUserByEmail(email);
		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles(getRoles(user)).build();
	}

	private String[] getRoles(User user) {
		return user.getRole().split(",");
	}
}