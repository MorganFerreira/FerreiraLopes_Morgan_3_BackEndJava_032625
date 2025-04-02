package com.p3backEnd.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.p3backEnd.dto.UsersDto;
import com.p3backEnd.model.Users;
import com.p3backEnd.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Read - Get one user 
	 * @param id The id of the user
	 * @return An User object full filled
	 */
//	@GetMapping("/user/{id}")
//	public Users getEmployee(@PathVariable("id") final Long id) {
//		Optional<Users> user = userService.getUser(id);
//		if(user.isPresent()) {
//			return user.get();
//		} else {
//			return null;
//		}
//	}
	
	/**
	 * Create - Add a new user
	 * @param user An object users
	 * @return The user object saved
	 */
	@PostMapping("/auth/register")
	public void createUsers(@RequestBody UsersDto user) {
		userService.saveUsers(user);
	}
}
