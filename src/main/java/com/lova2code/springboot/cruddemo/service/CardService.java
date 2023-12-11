package com.lova2code.springboot.cruddemo.service;

import com.lova2code.springboot.cruddemo.entity.Card;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardService {

    ResponseEntity<List<Card>> findAll();

    ResponseEntity<Card> readCard(String theToken);

    boolean viewStatus(String theToken);

    Card save(Card card);

    Card update(Card card, String theToken);

    void deleteById(String theToken);

    void disable(String cardToken);
}
