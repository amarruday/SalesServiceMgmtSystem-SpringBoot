package com.yashsales.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.constants.JwtUtils;
import com.yashsales.dto.JwtRequest;
import com.yashsales.dto.JwtResponse;
import com.yashsales.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetails;

	@Autowired
	private JwtUtils jwtUtils;

	// generate token API
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		JwtResponse resp = new JwtResponse();
		resp = new JwtResponse();
		
		try {
			this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			resp.setResponseStatus(ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
			resp.setMessage("Wrong Username/Password Combination.");
			return ResponseEntity.ok(resp);
		}

		// authenticate
		UserDetails theUserDetails = this.userDetails.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtils.generateToken(theUserDetails);
		resp.setResponseStatus(ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
		resp.setToken(token);
		return ResponseEntity.ok(resp);
	}

	private void authenticate(String username, String password) throws UsernameNotFoundException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw e;
		} catch (BadCredentialsException bce) {
			throw bce;
		}
	}
};