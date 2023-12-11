package com.lova2code.springboot.cruddemo.rest;

import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.entity.ResponseStatus;
import com.lova2code.springboot.cruddemo.exception.CardNotFoundException;
import com.lova2code.springboot.cruddemo.service.CardService;
import com.lova2code.springboot.cruddemo.service.Microservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    private final Microservice microservice;

    @GetMapping("/cards")
    @Operation(summary = "Display all cards in repository", description = "Endpoint to obtain the cards list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards list obtain with success"),
            @ApiResponse(responseCode = "204", description = "Cards list empty")
    })
    public ResponseEntity<List<Card>> findAll() {
        log.info("Display all cards");
        log.warn("Context: " + microservice.getContextPath());
        List<Card> cards = cardService.findAll();

        if (cards.isEmpty()) {
            log.warn("No card in repo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/cards/{cardToken}")
    @Operation(summary = "Display info for the selected card", description = "Endpoint to obtain the info for the selected card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card info obtained with success")
    })
    public ResponseEntity<Card> readCard(@PathVariable String cardToken) {
        return new ResponseEntity<>(cardService.readCard(cardToken), HttpStatus.OK);
    }

    @GetMapping("/cards/status/{cardToken}")
    public ResponseEntity<ResponseStatus> viewStatus(@PathVariable String cardToken) {
        boolean tmp = cardService.viewStatus(cardToken);
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setEnabled(false);
        if (tmp) {
            responseStatus.setEnabled(true);
        }
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @PostMapping("/cards")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card theCard = cardService.save(card);
        return new ResponseEntity<>(theCard, HttpStatus.OK);
    }

    @PutMapping ("/cards/{cardToken}")
    public ResponseEntity<Card> updateCard(@RequestBody Card card, @PathVariable String cardToken) {
        Card theCard = cardService.update(card, cardToken);
        return new ResponseEntity<>(theCard, HttpStatus.OK);
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
