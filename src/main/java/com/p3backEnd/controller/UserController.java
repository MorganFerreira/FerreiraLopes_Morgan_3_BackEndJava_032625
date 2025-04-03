package com.p3backEnd.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p3backEnd.configuration.SpringSecurityConfig;
import com.p3backEnd.dto.UsersDto;
import com.p3backEnd.mappers.UsersMapper;
import com.p3backEnd.model.Users;
import com.p3backEnd.service.JWTService;
import com.p3backEnd.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	private JWTService jwtService;
	private SpringSecurityConfig springSecurityConfig;
	private UsersMapper usersMapper;
	
	private AuthenticationManager authenticationManager;
	
	public UserController(UserService userService,
						  JWTService jwtService,
						  SpringSecurityConfig springSecurityConfig,
						  UsersMapper usersMapper) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.springSecurityConfig = springSecurityConfig;
		this.usersMapper = usersMapper;
	}

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
	 * Register - Add a new user
	 * @param RegisterRequest An object users
	 * @return Token
	 */
    @PostMapping("/auth/register")
    public ResponseEntity<ResponseToken> registerUser(@RequestBody RegisterRequest body) {

        Users newUser = userService.saveUsers(body.email, body.name, body.password);
		if (newUser != null) {
			String token = jwtService.generateTokenForRegistering(newUser.getName());
			return ResponseEntity.ok(new ResponseToken(token));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

    @GetMapping("/auth/me")
    public ResponseEntity<UsersDto> currentUser(@RequestHeader(name = "Authorization") String header) {
       
        String jwtToken = header.replace("Bearer ", "");
        Jwt jwt = springSecurityConfig.jwtDecoder().decode(jwtToken);
        String name = jwt.getSubject();
        Users user = userService.getUserByName(name);
        UsersDto userDto = usersMapper.mapToDto(user);
        if(userDto != null){
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
	/**
	 * Login - Log an user
	 * @param Authentication An object authentication
	 * @return Token
	 */
	@PostMapping("/auth/login")
	public String getToken(Authentication authentication) {
		String token = jwtService.generateToken(authentication);
		return token;
	}
	
    public record RegisterRequest(String email, String password, String name){}
    public record LoginBody(String email, String password){}
    public record ResponseToken(String token){}
}
