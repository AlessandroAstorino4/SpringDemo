package com.lova2code.springboot.cruddemo.service;

import com.lova2code.springboot.cruddemo.dao.CardRepository;
import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.entity.CodeGenerator;
import com.lova2code.springboot.cruddemo.exception.CardDisabledException;
import com.lova2code.springboot.cruddemo.exception.CardNotFoundException;
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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Card> readCard(String theToken) {

        Optional<Card> result = cardRepository.findById(theToken);

        if (result.isPresent()) {
            log.info("Card found in repo");
            Card theCard = result.get();
            if (theCard.isEnabled()) {
                log.info("Card Enabled");
                return new ResponseEntity<>(theCard, HttpStatus.OK);
            } else {
                log.warn("Card Disabled");
                throw new CardDisabledException("Card found but is disabled");
            }
        } else {
            log.error("Card not found in repository");
            throw new CardNotFoundException("Card not found");
        }
    }

    @Override
    public boolean viewStatus(String theToken) {
        Optional<Card> res = cardRepository.findById(theToken);
        Card theCard = null;

        if (res.isPresent()) {
            log.info("Getting card info");
            theCard = res.get();
        } else {
            log.error("Card not found in repository");
            throw new CardNotFoundException("Did not find card token: " + theToken);
        }
        return theCard.isEnabled();
    }

    @Transactional
    @Override
    public Card save(Card card) {
        List<Card> allCards = cardRepository.findAll();
        for (Card c : allCards) {
            if (card.getPan().equals(c.getPan()) && !(c.isEnabled())) {
                log.info("Card alreay present in repo. Update status");
                c.setEnabled(true);
                return cardRepository.save(c);
            }
        }
        log.info("Generate card token...");
        log.info("Card insert in repo");
        card.setToken(codeGenerator.generateRandomAlphaNumericString(16));
        return cardRepository.save(card);
    }

    @Transactional
    @Override
    public Card update(Card card, String theToken) {
        Optional<Card> res = cardRepository.findById(theToken);
        Card theCard = null;

        if (res.isPresent()) {
            log.info("Card found");
            theCard = res.get();
            if (theCard.isEnabled()) {
                log.info("Card Enabled");
                theCard.setExpire(card.getExpire());
                theCard.setCvv(card.getCvv());
                return cardRepository.save(theCard);
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
    public void deleteById(String theToken) {
        Optional<Card> card = cardRepository.findById(theToken);
        Card theCard = null;

        if (card.isPresent()) {
            theCard = card.get();
            log.info("Card found");
            cardRepository.deleteById(theToken);
        } else {
            log.error("Card not found in repo");
            throw new CardNotFoundException("Did not find card token: " + theToken);
        }
    }

    @Transactional
    @Override
    public void disable(String cardToken) {
        Optional<Card> card = cardRepository.findById(cardToken);
        Card theCard = null;

        if (card.isPresent()) {
            theCard = card.get();
            theCard.setEnabled(false);
            log.warn("Setting card status disabled");
            cardRepository.save(theCard);
        } else {
            log.error("Card not found in repo");
            throw new CardNotFoundException("Did not find card token: " + cardToken);
        }
    }
}