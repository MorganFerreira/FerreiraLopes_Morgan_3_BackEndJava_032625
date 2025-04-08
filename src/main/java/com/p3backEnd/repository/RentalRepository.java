package com.p3backEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p3backEnd.model.Rentals;

@Repository
public interface RentalRepository extends JpaRepository <Rentals, Integer> {

}
