package com.p3backEnd.model;

import java.util.Date;

import jakarta.persistence.Entity;

@Entity
public class rental {

	private int id;
	private String name;
	private int surface;
	private int price;
	private String picture;
	private String description;
	private int owner_id;
	private Date created_at;
	private Date updated_at;

}
