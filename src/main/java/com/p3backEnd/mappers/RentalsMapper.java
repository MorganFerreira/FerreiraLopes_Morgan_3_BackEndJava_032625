package com.p3backEnd.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.p3backEnd.dto.RentalsDto;
import com.p3backEnd.model.Rentals;

@Component
public class RentalsMapper {

    @Bean
    public ModelMapper rentalModelMapper() {
        return new ModelMapper();
    }
	
	public RentalsDto mapToDto(Rentals rentals){
        return rentalModelMapper().map(rentals, RentalsDto.class);
    }

    public Rentals mapToEntity(RentalsDto rentalsDto){
        return rentalModelMapper().map(rentalsDto, Rentals.class);
    }
    
	public RentalsDto mapToDtoWithOptional(Object rentals){
        return rentalModelMapper().map(rentals, RentalsDto.class);
    }

    public Rentals mapToEntityWithOptional(Object rentalsDto){
        return rentalModelMapper().map(rentalsDto, Rentals.class);
    }
}
