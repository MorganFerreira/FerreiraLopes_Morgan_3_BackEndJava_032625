package com.p3backEnd.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.p3backEnd.model.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
	Users findByName(String name);
}
