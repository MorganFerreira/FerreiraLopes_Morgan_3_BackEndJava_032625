package com.p3backEnd.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.p3backEnd.model.Rentals;
import com.p3backEnd.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

	private final RentalRepository rentalRepository;

	public List<Rentals> getAllRentals() {
		return rentalRepository.findAll();
	}

	public Rentals createRental(Rentals newRental, Integer userId, String picturePath) {
		newRental.setCreated_at(LocalDateTime.now());
		newRental.setUpdated_at(LocalDateTime.now());
		newRental.setOwner_id(userId);
		newRental.setPicture(picturePath);
		return rentalRepository.save(newRental);
	}
	
	public Optional<Rentals> getRentalById(String id) {
		Integer ident = Integer.parseInt(id);
		return rentalRepository.findById(ident);
	}

	public String updateRental(String id, Rentals newRental) {
        Optional<Rentals> rentalOptional = rentalRepository.findById(Integer.parseInt(id));
        if(rentalOptional.isPresent()){
            Rentals rentalToModify = rentalOptional.get();
            newRental.setDescription(rentalToModify.getDescription());
            newRental.setName(rentalToModify.getName());
            newRental.setPrice(rentalToModify.getPrice());
            newRental.setSurface(rentalToModify.getSurface());
            newRental.setOwner_id(rentalToModify.getOwner_id());
            newRental.setUpdated_at(LocalDateTime.now());
            rentalRepository.save(newRental);
            return "Rental updated";
        } else {
            return "Rental not found";
        }
	}
}
