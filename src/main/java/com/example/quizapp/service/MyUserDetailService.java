package com.example.quizapp.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.quizapp.entity.MyUser;
import com.example.quizapp.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
	Logger log = LoggerFactory.getLogger(MyUserDetailService.class);
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("This came in the loadUserByUsername MyUserDetailService  service   {} ",username);
		

		Optional<MyUser> user = userRepository.findByUsername(username);

		if (user.isPresent()) {
			var userObj = user.get();
			return User.builder().username(userObj.getUsername()).password(userObj.getPassword())
					.roles(getRoles(userObj)).build();
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	private String[] getRoles(MyUser user) {
		if (user.getRole() == null) {
			return new String[] { "Student" };
		}
		return user.getRole().split(",");
	}
}