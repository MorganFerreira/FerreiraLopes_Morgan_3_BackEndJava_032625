package com.p3backEnd.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.p3backEnd.dto.RentalsDto;
import com.p3backEnd.dto.RentalsWithPathForPicturesDto;
import com.p3backEnd.mappers.RentalsMapper;
import com.p3backEnd.model.Rentals;
import com.p3backEnd.model.Users;
import com.p3backEnd.service.JWTService;
import com.p3backEnd.service.RentalService;
import com.p3backEnd.service.StorageService;
import com.p3backEnd.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	@Autowired
	private RentalService rentalService;
	@Autowired
	private final UserService userService;
	private final RentalsMapper rentalsMapper;
	private final StorageService storageService;
	private final JWTService jwtService;
	
	public RentalController(RentalService rentalService,
							RentalsMapper rentalsMapper,
							UserService userService,
							StorageService storageService,
							JWTService jwtService) {
		this.rentalService = rentalService;
		this.rentalsMapper = rentalsMapper;
		this.userService = userService;
		this.storageService = storageService;
		this.jwtService = jwtService;
	}

	/**
	 * Get All Rentals
	 * @param 
	 * @return List of Rentals
	 */
	@Operation(summary = "Get all rentals", description = "Retourne toutes les locations")
    @GetMapping("")
    public ResponseEntity<RentalsResponse> findAllRentals() {
        List<Rentals> rentals = rentalService.getAllRentals();        
        if(rentals != null){
            List<RentalsWithPathForPicturesDto> rentalDtos = rentals.stream()
            									 					.map(rental -> rentalsMapper.mapToDtoWithPathForPictures(rental))
            									 					.collect(Collectors.toList());
            return ResponseEntity.ok(new RentalsResponse(rentalDtos));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
	 * Get Rental by Id
	 * @param Id
	 * @return Rental
	 */
	@Operation(summary = "Get rental by id", description = "Retourne une location par son id")
    @GetMapping("/{id}")
    public ResponseEntity<RentalsWithPathForPicturesDto> getRentalById(@PathVariable String id) {
        Optional<Rentals> rental = rentalService.getRentalById(id);
        if(rental != null){
            RentalsWithPathForPicturesDto rentalDto = rentalsMapper.mapToDtoWithOptionalAndPicture(rental);
            return ResponseEntity.ok(rentalDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
	/**
	 * Create Rental
	 * @param FormData
	 * @return RentalResponse
	 * @throws Exception 
	 */
	@Operation(summary = "Create rental", description = "Créés une location avec en paramètres son nom, sa surface, son prix, son image, sa description et l'id de l'utilisateur qui la créée")
    @PostMapping("")
    public ResponseEntity<RentalResponse> createRental(@RequestHeader("Authorization") String token, @ModelAttribute RentalsDto rentalsDto) throws Exception {
        String userName = jwtService.getNameFromToken(token);
        Users user =  userService.getUserByName(userName);
        String fileName = null;
        MultipartFile file = rentalsDto.getPicture();
            if (file != null && !file.isEmpty()) {
                fileName = storageService.store(file);                
            }
       rentalService.createRental(rentalsMapper.mapToEntity(rentalsDto), user.getId(), fileName);
       return ResponseEntity.ok(new RentalResponse("Location créée !!!"));
       }

	/**
	 * Modify Rental by Id
	 * @param Id and FormData
	 * @return RentalResponse
	 */
	@Operation(summary = "Update rental", description = "Modifie une location avec en paramètres son nom, sa surface, son prix, sa description et l'id de l'utilisateur qui la modifie")
    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable String id, @ModelAttribute RentalsDto rentalsDto) {
        String message = rentalService.updateRental(id, rentalsMapper.mapToEntity(rentalsDto));
        if(message == "Rental updated"){
            return ResponseEntity.ok(new RentalResponse(message));
        } else {
            return ResponseEntity.status(404).build();
        }
    }
    
    public record CreateRentalRequest(
    		String name, String surface, String price, MultipartFile file, String description){}
    public record RentalResponse(String response){}
    public record RentalsResponse(List<RentalsWithPathForPicturesDto> rentals){}
}
