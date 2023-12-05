package com.lova2code.springboot.cruddemo.dao;

import com.lova2code.springboot.cruddemo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
