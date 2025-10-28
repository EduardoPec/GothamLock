package com.wayne.waynesecurity.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.Role;
import com.wayne.waynesecurity.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Email não encontrado: " + username));

		Set<GrantedAuthority> authorities = mapRolesToAuthorities(user.getRole());

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				authorities 
		);
	}
    
    private Set<GrantedAuthority> mapRolesToAuthorities(Role role) {
		return Set.of(new SimpleGrantedAuthority("ROLE_" + role.toString()))
				.stream().collect(Collectors.toSet());
	}
}