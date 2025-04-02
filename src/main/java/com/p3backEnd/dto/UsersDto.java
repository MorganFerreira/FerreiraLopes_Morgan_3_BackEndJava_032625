package com.p3backEnd.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersDto {

	private String email;
	private String name;
	private String password;

}
