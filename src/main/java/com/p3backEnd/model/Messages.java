package com.p3backEnd.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Messages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(optional = false, targetEntity = Rentals.class)
	private Integer rental_id;
	@ManyToOne(optional = false, targetEntity = Users.class)
	private Integer user_id;
	private String message;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at;
	
	@LastModifiedDate
	private LocalDateTime updated_at;

	Messages(Integer rental_id, Integer user_id, String message){
		this.rental_id = rental_id; 
		this.user_id = user_id;
		this.message = message;
	}

}
