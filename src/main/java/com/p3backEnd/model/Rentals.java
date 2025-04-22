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
public class Rentals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private Integer surface;
	private Integer price;
	private String picture;
	private String description;
	@ManyToOne(optional = false, targetEntity = Users.class)
	private Integer owner_id;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at;
	
	@LastModifiedDate
	private LocalDateTime updated_at;

}
