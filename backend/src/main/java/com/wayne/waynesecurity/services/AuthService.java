package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.Role;
import com.wayne.waynesecurity.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

	private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));

		Set<GrantedAuthority> authorities = mapRolesToAuthorities(user.getRole());

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				authorities 
		);
	}
    
    private Set<GrantedAuthority> mapRolesToAuthorities(Role role) {
		return Set.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}
}