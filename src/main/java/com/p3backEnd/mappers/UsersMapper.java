package com.p3backEnd.mappers;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.p3backEnd.dto.UsersDto;
import com.p3backEnd.model.Users;

@Component
public class UsersMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
	public UsersDto mapToDto(Users users){
        return modelMapper().map(users, UsersDto.class);
    }

    public Users mapToEntity(UsersDto usersDto){
        return modelMapper().map(usersDto, Users.class);
    }

}
