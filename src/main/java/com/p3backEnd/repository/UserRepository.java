package com.p3backEnd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p3backEnd.model.Users;

@Repository
public interface UserRepository extends JpaRepository <Users, Integer> {
	Users findByName(String name);
	Users findByEmail(String email);
}
