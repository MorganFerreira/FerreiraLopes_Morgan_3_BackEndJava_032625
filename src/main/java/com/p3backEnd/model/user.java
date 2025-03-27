package com.p3backEnd.model;

import java.util.Date;

import jakarta.persistence.Entity;

@Entity
public class user {

	private int id;
	private String name;
	private String email;
	private Date created_at;
	private Date updated_at;
	
}
