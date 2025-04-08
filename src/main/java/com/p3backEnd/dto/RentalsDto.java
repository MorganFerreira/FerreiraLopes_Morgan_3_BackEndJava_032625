package com.p3backEnd.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalsDto {

	private Integer id;
	private String name;
	private Integer surface;
	private Integer price;
	private String picture;
	private String description;
	private Integer owner_id;
	private String created_at;
	private String updated_at;
	
	public RentalsDto(Integer id, String name, Integer surface, Integer price, String picture, String description,
            Integer owner_id, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.owner_id = owner_id;
        
		// Convert LocalDateTime to String
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.created_at = created_at.format(formatter);
        this.updated_at = created_at.format(formatter);
	}
}
