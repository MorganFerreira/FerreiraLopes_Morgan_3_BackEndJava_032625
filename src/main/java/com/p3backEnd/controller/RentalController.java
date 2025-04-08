package com.p3backEnd.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.p3backEnd.configuration.SpringSecurityConfig;
import com.p3backEnd.dto.RentalsDto;
import com.p3backEnd.mappers.RentalsMapper;
import com.p3backEnd.model.Rentals;
import com.p3backEnd.model.Users;
import com.p3backEnd.service.RentalService;
import com.p3backEnd.service.StorageService;
import com.p3backEnd.service.UserService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	@Autowired
	private RentalService rentalService;
	@Autowired
	private final UserService userService;
	private final RentalsMapper rentalsMapper;
	private final SpringSecurityConfig springSecurityConfig;
	private final StorageService storageService;
	
	public RentalController(RentalService rentalService,
							RentalsMapper rentalsMapper,
							SpringSecurityConfig springSecurityConfig,
							UserService userService,
							StorageService storageService) {
		this.rentalService = rentalService;
		this.rentalsMapper = rentalsMapper;
		this.springSecurityConfig = springSecurityConfig;
		this.userService = userService;
		this.storageService = storageService;
	}

	/**
	 * Get All Rentals
	 * @param 
	 * @return List of Rentals
	 */
    @GetMapping("")
    public ResponseEntity<RentalsResponse> findAllRentals() {
        List<Rentals> rentals = rentalService.getAllRentals();        
        if(rentals != null){
            List<RentalsDto> rentalDtos = rentals.stream()
            									 .map(rental -> rentalsMapper.mapToDto(rental))
            									 .collect(Collectors.toList());
            return ResponseEntity.ok(new RentalsResponse(rentalDtos));
        } else {
            return ResponseEntity.status(404).build();
        }
    }
    
	/**
	 * Create Rental
	 * @param FormData
	 * @return RentalResponse
	 * @throws Exception 
	 */
    @PostMapping("")
    public ResponseEntity<RentalResponse> createRental(MultipartHttpServletRequest createRequest) throws Exception {
        String token = createRequest.getHeader("Authorization").replace("Bearer", "");
        Jwt jwt = springSecurityConfig.jwtDecoder().decode(token);
        String userName = jwt.getSubject();
        Users user =  userService.getUserByName(userName);
        Rentals newRental = new Rentals();
        String description = createRequest.getParameter("description");
        Integer surface = Integer.parseInt(createRequest.getParameter("surface"));
        Integer price = Integer.parseInt(createRequest.getParameter("price"));
        String rentalName = createRequest.getParameter("name");
        newRental.setDescription(description);
        newRental.setName(rentalName);
        newRental.setSurface(surface);
        newRental.setPrice(price);
        newRental.setOwner_id(user.getId());

        MultipartFile file = createRequest.getFile("picture");
        if (file != null && !file.isEmpty()) {
            String fileName = "http://localhost:8080/" + file.getOriginalFilename();
            storageService.store(file);
            newRental.setPicture(fileName);
        }
        
        rentalService.createRental(newRental);
        return ResponseEntity.ok(new RentalResponse("Location créée !!!"));
    }

	/**
	 * Get Rental by Id
	 * @param Id
	 * @return Rental
	 */
    @GetMapping("/{id}")
    public ResponseEntity<RentalsDto> getRentalById(@PathVariable String id) {
        Optional<Rentals> rental = rentalService.getRentalById(id);
        if(rental != null){
            RentalsDto rentalDto = rentalsMapper.mapToDtoWithOptional(rental);
            return ResponseEntity.ok(rentalDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

	/**
	 * Modify Rental by Id
	 * @param Id and FormData
	 * @return RentalResponse
	 */
    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable String id, MultipartHttpServletRequest modifyRequest) {
        String token = modifyRequest.getHeader("Authorization").replace("Bearer", "");
        Jwt jwt = springSecurityConfig.jwtDecoder().decode(token);
        String name = jwt.getSubject();
        Users user =  userService.getUserByName(name);
        Rentals newRental = new Rentals();
        
        String description = modifyRequest.getParameter("description");
        Integer surface =  Integer.parseInt(modifyRequest.getParameter("surface"));
        Integer price = Integer.parseInt(modifyRequest.getParameter("price"));
        String rentalName = modifyRequest.getParameter("name");
        newRental.setDescription(description);
        newRental.setSurface(surface);
        newRental.setPrice(price);
        newRental.setName(rentalName);
        newRental.setOwner_id(user.getId());

        String message = rentalService.updateRental(id, newRental);
        if(message == "Rental updated"){
            return ResponseEntity.ok(new RentalResponse(message));
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
    public record CreateRentalRequest(
    		String name, String surface, String price, MultipartFile file, String description){}
    public record RentalResponse(String response){}
    public record RentalsResponse(List<RentalsDto> rentals){}
    
}
