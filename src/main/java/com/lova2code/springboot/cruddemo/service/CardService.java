package com.lova2code.springboot.cruddemo.service;

import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.entity.ResponseStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardService {

    ResponseEntity<List<Card>> findAll();

    ResponseEntity<Card> readCard(String theToken);

    ResponseEntity<ResponseStatus> viewStatus(String theToken);

    ResponseEntity<Card> save(Card card);

    ResponseEntity<Card> update(Card card, String theToken);

    ResponseEntity<Card> deleteById(String theToken);

    ResponseEntity<Card> disable(String cardToken);

    ResponseEntity<Card> delete(String cardToken, boolean forced);
}
