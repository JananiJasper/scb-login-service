package com.example.loginservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginservice.model.AuthenticationRequest;
import com.example.loginservice.model.AuthenticationResponse;
import com.example.loginservice.model.MyUserDetails;
import com.example.loginservice.service.JPAUserDetailsService;
import com.example.loginservice.util.JwtUtil;

  
@Validated                               
@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private JPAUserDetailsService userDetailsService;

	@PostMapping(value = "/login")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
	                               HttpHeaders responseHeaders = new HttpHeaders();
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		//if authentication is successful else throw an exception
		 MyUserDetails userDetails = (MyUserDetails) userDetailsService
			.loadUserByUsername(authenticationRequest.getUsername());
		 String jwt = jwtTokenUtil.generateToken(userDetails);
		 
		AuthenticationResponse response = new AuthenticationResponse(jwt);

		response.setId(userDetails.getId());
		response.setUsername(userDetails.getUsername());
		
		List<String> roles = new ArrayList<String>();
		userDetails.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
		response.setRoles(roles);

		return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
	
	}
	
	@GetMapping(value = "/authenticate")	
	public ResponseEntity<String> LoginSuccesswithJwt() throws Exception {
	HttpHeaders responseHeaders = new HttpHeaders();
	return new ResponseEntity<>("User Authenticated and Login successfull", responseHeaders, HttpStatus.OK);
	}
}