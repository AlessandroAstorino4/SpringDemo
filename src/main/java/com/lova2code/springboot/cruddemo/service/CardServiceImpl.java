package com.lova2code.springboot.cruddemo.service;

import com.lova2code.springboot.cruddemo.dao.CardRepository;
import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.entity.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    private CodeGenerator codeGenerator;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, CodeGenerator codeGenerator) {

        this.cardRepository = cardRepository;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public List<Card> findAll() {

        return cardRepository.findAll();
    }

    @Override
    public Card readCard(String theToken) {
        Optional<Card> result = cardRepository.findById(theToken);
        Card theCard = null;

        if (result.isPresent()) {
            theCard = result.get();
            if (theCard.isEnabled()) {
                return theCard;
            } else {
                throw new RuntimeException("Card found but is disabled with token: " + theToken);
            }
        } else {
            throw new RuntimeException("Did not find card token: " + theToken);
        }
    }


    @Override
    public boolean viewStatus(String theToken) {
        Optional<Card> res = cardRepository.findById(theToken);
        Card theCard = null;

        if (res.isPresent()) {
            theCard = res.get();
        } else {
            throw new RuntimeException("Did not find card token: " + theToken);
        }
        return theCard.isEnabled();
    }

    @Transactional
    @Override
    public Card save(Card card) {
        List<Card> allCards = cardRepository.findAll();
        for (Card c : allCards) {
            if (card.getPan().equals(c.getPan()) && !(c.isEnabled())) {
                c.setEnabled(true);
                return cardRepository.save(c);
            }
        }
        card.setToken(codeGenerator.generateRandomAlphaNumericString(16));
        return cardRepository.save(card);
    }

    @Transactional
    @Override
    public Card update(Card card, String theToken) {
        Optional<Card> res = cardRepository.findById(theToken);
        Card theCard = null;

        if (res.isPresent()) {
            theCard = res.get();
            if (theCard.isEnabled()) {
                theCard.setExpire(card.getExpire());
                theCard.setCvv(card.getCvv());
                return cardRepository.save(theCard);
            } else {
                throw new RuntimeException("Could not perform update for card token: " + theToken + "|| Card disabled.");
            }
        } else {
            throw new RuntimeException("Could not perform update for card token: " + theToken);
        }

    }

    @Transactional
    @Override
    public void deleteById(String theToken) {
        Optional<Card> card = cardRepository.findById(theToken);
        Card theCard = null;

        if (card.isPresent()) {
            theCard = card.get();
            cardRepository.deleteById(theToken);
        } else {
            throw new RuntimeException("Did not find card token: " + theToken);
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
            cardRepository.save(theCard);
        } else {
            throw new RuntimeException("Did not find card token: " + cardToken);
        }
    }
}
