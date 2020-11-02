package com.example.loginservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.loginservice.model.MyUserDetails;
import com.example.loginservice.model.User;
import com.example.loginservice.repository.UserRepository;

@Service
public class JPAUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return user.map(MyUserDetails::new).get();
		
	}


}