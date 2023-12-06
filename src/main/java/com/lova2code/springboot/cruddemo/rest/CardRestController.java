package com.lova2code.springboot.cruddemo.rest;

import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.exception.CardNotFoundException;
import com.lova2code.springboot.cruddemo.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class CardRestController {

    private CardService cardService;


    @Autowired
    public CardRestController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cards")
    public List<Card> findAll() {
        log.info("Display all cards");
        return cardService.findAll();
    }

    @GetMapping("/cards/{cardToken}")
    public Card readCard(@PathVariable String cardToken) {
        Card card =  cardService.readCard(cardToken);
        return card;
    }

    @GetMapping("/cards/status/{cardToken}")
    public String viewStatus(@PathVariable String cardToken) {
        boolean tmp = cardService.viewStatus(cardToken);
        String stat = "DISABLED";
        if (tmp) {
            stat = "ENABLED";
        }
        return "Card token: " + cardToken + " has status: " + stat;
    }

    @PostMapping("/cards")
    public String addCard(@RequestBody Card card) {
        Card theCard = cardService.save(card);
        return theCard.getToken();
    }

    @PutMapping ("/cards/{cardToken}")
    public String updateCard(@RequestBody Card card, @PathVariable String cardToken) {
        Card theCard = cardService.update(card, cardToken);
        return theCard.getToken();
    }

    @DeleteMapping("/cards/{cardToken}")
    public void deleteById(@PathVariable String cardToken, @RequestParam boolean forced) {
        if (forced) {
            cardService.deleteById(cardToken);
        } else {
            cardService.disable(cardToken);
        }

    }

}
