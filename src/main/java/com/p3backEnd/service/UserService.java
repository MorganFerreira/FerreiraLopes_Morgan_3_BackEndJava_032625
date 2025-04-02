package com.p3backEnd.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
//	public Optional<Users> getUser(final Long id) {
//        return userRepository.findById(id);
//    }
	
    public void saveUsers(UsersDto users) {
		Users savedUsers = UsersMapper.mapToEntity(users);
		userRepository.save(savedUsers);
    }
}
