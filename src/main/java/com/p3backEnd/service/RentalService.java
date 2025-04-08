package com.p3backEnd.service;

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

	public Rentals createRental(Rentals newRental) {
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
            if(newRental.getDescription() == null ){
            	newRental.setDescription(rentalToModify.getDescription());
            } 
            if(newRental.getName() == null){
            	newRental.setName(rentalToModify.getName());
            }
            if(newRental.getPicture() == null){
            	newRental.setPicture(rentalToModify.getPicture());
            }
            if(newRental.getOwner_id() == null){
            	newRental.setOwner_id(rentalToModify.getOwner_id());
            }
            if(newRental.getPrice() == null){
            	newRental.setPrice(rentalToModify.getPrice());
            }
            if(newRental.getSurface() ==  null){
            	newRental.setSurface(rentalToModify.getSurface());
            }
            newRental.setId(Integer.parseInt(id));
            rentalRepository.save(newRental);
            return "Rental updated";
        } else {
            return "Rental not found";
        }
	}
}
