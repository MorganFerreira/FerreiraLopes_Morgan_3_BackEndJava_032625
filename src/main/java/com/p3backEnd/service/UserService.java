package com.p3backEnd.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p3backEnd.configuration.SpringSecurityConfig;
import com.p3backEnd.dto.UsersDto;
import com.p3backEnd.mappers.UsersMapper;
import com.p3backEnd.model.Users;
import com.p3backEnd.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SpringSecurityConfig springSecurityConfig;
	
//	public Optional<Users> getUser(final Long id) {
//        return userRepository.findById(id);
//    }
	
    public Users saveUsers(String email, String name, String password) {
		Users newUsers = new Users(email, name, password);
		String visiblePassword = newUsers.getPassword();
		newUsers.setPassword(springSecurityConfig.passwordEncoder().encode(visiblePassword));
		return userRepository.save(newUsers);
    }
    
    public Users getUserByName(String name){
        return userRepository.findByName(name);
    }
    
    public Optional<Users> getUserById(String id){
        Integer ident = Integer.parseInt(id);
        return userRepository.findById(ident);
    }
}
