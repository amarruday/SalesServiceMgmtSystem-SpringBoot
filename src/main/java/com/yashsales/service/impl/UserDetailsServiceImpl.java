package com.yashsales.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yashsales.entity.AuthUser;
import com.yashsales.entity.User;
import com.yashsales.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
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
		}
	}

}
