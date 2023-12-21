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
    @Operation(summary = "Display the status for the selected card", description = "Endpoint to obtain the status for the selected card ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card status obtained with success"),
            @ApiResponse(responseCode = "404", description = "Card not found in repo")
    })
    public ResponseEntity<ResponseStatus> viewStatus(@PathVariable String cardToken) {
        return cardService.viewStatus(cardToken);
    }

    @PostMapping("/cards")
    @Operation(summary = "Add a card to the repo", description = "Endpoint to add a card to the repo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card has been added to the repo")
    })
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        return cardService.save(card);
    }

    @PutMapping ("/cards/{cardToken}")
    @Operation(summary = "Update the card field", description = "Endpoint to update the card info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card has been updated"),
            @ApiResponse(responseCode = "400", description = "Card found but is disabled"),
            @ApiResponse(responseCode = "404", description = "Card not found in repo")
    })
    public ResponseEntity<Card> updateCard(@RequestBody Card card, @PathVariable String cardToken) {
        return  cardService.update(card, cardToken);
    }

    @Operation(summary = "Delete a card", description = "Endopint to delete a card from repo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card deleted"),
            @ApiResponse(responseCode = "404", description = "Card not found in repo")
    })
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
