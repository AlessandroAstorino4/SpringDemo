package com.lova2code.springboot.cruddemo.rest;

import com.lova2code.springboot.cruddemo.entity.Card;
import com.lova2code.springboot.cruddemo.entity.ResponseStatus;
import com.lova2code.springboot.cruddemo.service.CardService;
import com.lova2code.springboot.cruddemo.service.MicroserviceProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final MicroserviceProperties microservice;

    @GetMapping("/cards")
    @Operation(summary = "Display all cards in repository", description = "Endpoint to obtain the cards list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards list obtain with success"),
            @ApiResponse(responseCode = "204", description = "Cards list empty")
    })
    public ResponseEntity<List<Card>> findAll() {
        return cardService.findAll();
    }

    @GetMapping("/cards/{cardToken}")
    @Operation(summary = "Display info for the selected card", description = "Endpoint to obtain the info for the selected card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card info obtained with success"),
            @ApiResponse(responseCode = "404", description = "Card not found in repo"),
            @ApiResponse(responseCode = "400", description = "Card found but is disabled")
    })
    public ResponseEntity<Card> readCard(@PathVariable String cardToken) {
        return cardService.readCard(cardToken);
    }

    @GetMapping("/cards/status/{cardToken}")
    public ResponseEntity<ResponseStatus> viewStatus(@PathVariable String cardToken) {
        return cardService.viewStatus(cardToken);
    }

    @PostMapping("/cards")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        return cardService.save(card);
    }

    @PutMapping ("/cards/{cardToken}")
    public ResponseEntity<Card> updateCard(@RequestBody Card card, @PathVariable String cardToken) {
        return  cardService.update(card, cardToken);
    }

    @DeleteMapping("/cards/{cardToken}")
    public ResponseEntity<Card> delete(@PathVariable String cardToken, @RequestParam boolean forced) {
        return cardService.delete(cardToken, forced);
    }

    /*
    @DeleteMapping("/cards/{cardToken}")
    public void deleteById(@PathVariable String cardToken, @RequestParam boolean forced) {
        if (forced) {
            cardService.deleteById(cardToken);
        } else {
            cardService.disable(cardToken);
        }

    }
     */



}
