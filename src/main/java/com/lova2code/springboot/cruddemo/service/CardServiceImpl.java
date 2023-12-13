package com.lova2code.springboot.cruddemo.service;

import com.lova2code.springboot.cruddemo.dao.CardRepository;
import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.entity.CodeGenerator;
import com.lova2code.springboot.cruddemo.entity.ResponseStatus;
import com.lova2code.springboot.cruddemo.exception.CardDisabledException;
import com.lova2code.springboot.cruddemo.exception.CardNotFoundException;
import com.lova2code.springboot.cruddemo.exception.CardsListEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CodeGenerator codeGenerator;


    @Override
    public ResponseEntity<List<Card>> findAll() {
        List<Card> cards = cardRepository.findAll();
        if (cards.isEmpty()) {
            log.warn("No card in repo");
            throw new CardsListEmptyException("No card in repo");
        }
        return new ResponseEntity<>(cardRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Card> readCard(String theToken) {

        Optional<Card> result = cardRepository.findById(theToken);

        if (result.isPresent()) {
            Card theCard = result.get();
            log.info("Card found in repo | Token: " + theToken);
            if (theCard.isEnabled()) {
                log.info("Card Enabled | Token: " + theToken);
                return new ResponseEntity<>(theCard, HttpStatus.OK);
            } else {
                log.warn("Card Disabled | Token: " + theToken);
                throw new CardDisabledException("Card found but is disabled");
            }
        } else {
            log.error("Card not found in repository | Token: " + theToken);
            throw new CardNotFoundException("Card not found");
        }
    }

    @Override
    public ResponseEntity<ResponseStatus> viewStatus(String theToken) {

        Optional<Card> res = cardRepository.findById(theToken);
        Card theCard = null;

        if (res.isPresent()) {
            log.info("Getting card info | Token: " + theToken);
            theCard = res.get();
        } else {
            log.error("Card not found in repository | Token: " + theToken);
            throw new CardNotFoundException("Did not find card token: " + theToken);
        }
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setEnabled(theCard.isEnabled());
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Card> save(Card card) {
        List<Card> allCards = cardRepository.findAll();
        for (Card c : allCards) {
            if (card.getPan().equals(c.getPan()) && !(c.isEnabled())) {
                log.info("Card alreay present in repo. Update status | Token: " + card.getToken());
                c.setEnabled(true);
                return new ResponseEntity<>(cardRepository.save(c), HttpStatus.OK);
            }
        }
        log.info("Generate card token...");
        log.warn("Card insert in repo");
        card.setToken(codeGenerator.generateRandomAlphaNumericString(16));
        log.info("Card insert in repo | Token: "+ card.getToken());
        return new ResponseEntity<>(cardRepository.save(card), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Card> update(Card card, String theToken) {
        Optional<Card> res = cardRepository.findById(theToken);
        Card theCard = null;

        if (res.isPresent()) {
            log.info("Card found");
            theCard = res.get();
            if (theCard.isEnabled()) {
                log.info("Card Enabled");
                theCard.setExpire(card.getExpire());
                theCard.setCvv(card.getCvv());
                return new ResponseEntity<>(cardRepository.save(theCard), HttpStatus.OK);
            } else {
                log.warn("Card disabled");
                throw new CardDisabledException("Could not perform update for card token: " + theToken + "|| Card disabled.");
            }
        } else {
            log.error("Card not found");
            throw new CardNotFoundException("Could not perform update for card token: " + theToken);
        }

    }

    @Transactional
    @Override
    public ResponseEntity<Card> deleteById(String theToken) {
        Optional<Card> card = cardRepository.findById(theToken);
        Card theCard = null;

        if (card.isPresent()) {
            theCard = card.get();
            log.info("Card found");
            cardRepository.deleteById(theToken);
            return new ResponseEntity<>(theCard, HttpStatus.OK);
        } else {
            log.error("Card not found in repo");
            throw new CardNotFoundException("Did not find card token: " + theToken);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Card> disable(String cardToken) {
        Optional<Card> card = cardRepository.findById(cardToken);
        Card theCard = null;

        if (card.isPresent()) {
            theCard = card.get();
            theCard.setEnabled(false);
            log.warn("Setting card status disabled");
            return new ResponseEntity<>(cardRepository.save(theCard), HttpStatus.OK);
        } else {
            log.error("Card not found in repo");
            throw new CardNotFoundException("Did not find card token: " + cardToken);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Card> delete(String cardToken, boolean forced) {
        if (forced) {
            return deleteById(cardToken);
        } else {
            return disable(cardToken);
        }
    }
}