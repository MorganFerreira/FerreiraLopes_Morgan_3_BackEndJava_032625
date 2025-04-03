package com.p3backEnd.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersDto {

	private Integer id;
	private String email;
	private String name;
	private String created_at;
	private String updated_at;

	public UsersDto(Integer id, String email, String name, LocalDateTime created_at, LocalDateTime updated_at) {
		this.id = id;
		this.email = email;
		this.name = name;
		
		// Convert LocalDateTime to String
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.created_at = created_at.format(formatter);
		this.updated_at = updated_at.format(formatter);
    }
}
