package com.p3backEnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.p3backEnd.model.Users;
import com.p3backEnd.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getGrantedAuthorities("USER"));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
    	return authorities;
    }
}
