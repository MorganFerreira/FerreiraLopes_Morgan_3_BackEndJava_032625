package com.p3backEnd.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p3backEnd.dto.UsersDto;
import com.p3backEnd.mappers.UsersMapper;
import com.p3backEnd.model.Users;
import com.p3backEnd.service.JWTService;
import com.p3backEnd.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	private JWTService jwtService;
	private UsersMapper usersMapper;


	
	public UserController(UserService userService,
						  JWTService jwtService,
						  UsersMapper usersMapper) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.usersMapper = usersMapper;
	}
	
	/**
	 * Register - Add a new user
	 * @param RegisterRequest An object users
	 * @return Token
	 */
	@Operation(summary = "Login", description = "Autorise un utilisateur à se connecter et retourne un token JWT")
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

	/**
	 * Authentify user
	 * @param Header
	 * @return UsersDto
	 */
	@Operation(summary = "Register a new user", description = "Vérifie si l'utilisateur est en BDD, crée l'utilisateur et retourne un token JWT")
    @GetMapping("/auth/me")
    public ResponseEntity<UsersDto> currentUser(@RequestHeader("Authorization") String token, String header) {      
        String userName = jwtService.getNameFromToken(token);
        
        Users user = userService.getUserByName(userName);
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
	@Operation(summary = "Get user informations", description = "Retourne le nom et l'email de l'utilisateur")
    @PostMapping("/auth/login")
    public ResponseEntity<ResponseToken> login(@RequestBody LoginRequest body) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(body.email, body.password);
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        String token = jwtService.generateToken(authenticationResponse);
        if (token != null) {
            return ResponseEntity.ok(new ResponseToken(token));
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
	/**
	 * Read - Get one user 
	 * @param id The id of the user
	 * @return An UsersDto object
	 */
	@Operation(summary = "Get user by id", description = "Retourne un utilisateur par son id")
    @GetMapping("/user/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable String id) {
        Optional<Users> user = userService.getUserById(id);
        if(user.isPresent()){
            UsersDto userDto = usersMapper.mapToDtoWithOptional(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public record RegisterRequest(String email, String password, String name){}
    public record LoginRequest(String email, String password){}
    public record ResponseToken(String token){}
}
