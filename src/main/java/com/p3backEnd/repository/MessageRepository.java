package com.p3backEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p3backEnd.model.Messages;

@Repository
public interface MessageRepository extends JpaRepository <Messages, Integer> {

}
