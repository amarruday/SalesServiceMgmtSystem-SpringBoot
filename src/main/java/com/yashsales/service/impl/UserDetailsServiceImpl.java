package com.yashsales.service.impl;

import com.yashsales.service.AccessUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yashsales.entity.AuthUser;
import com.yashsales.entity.User;
import com.yashsales.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*User user = userRepo.findByUsername(username);
		//Optional<User> = user;
		try {
			if(user == null) {
				throw new UsernameNotFoundException("User not Found...!");
			}
			else {
				return new AuthUser(user);
			}
		} 
		catch(UsernameNotFoundException e) {
			throw e;
		}*/



		User user = userRepo.findByUsername(username);
		Optional<User> optionalUser = Optional.of(user);
		return optionalUser.map(AccessUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
	}

}
