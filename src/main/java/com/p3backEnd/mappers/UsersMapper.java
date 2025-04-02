package com.p3backEnd.mappers;

import com.p3backEnd.dto.UsersDto;
import com.p3backEnd.model.Users;

public class UsersMapper {

	public static UsersDto mapToDto(Users users){

		UsersDto usersDto =  UsersDto.builder()
                .email(users.getEmail())
                .name(users.getName())
                .password(users.getPassword())
                .build();

        return usersDto;
    }

    public static Users mapToEntity(UsersDto usersDto){

    	Users users = Users.builder()
                .email(usersDto.getEmail())
                .name(usersDto.getName())
                .password(usersDto.getPassword())
                .build();

        return users;
    }

}
