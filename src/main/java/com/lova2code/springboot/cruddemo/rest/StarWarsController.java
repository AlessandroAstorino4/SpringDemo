package com.lova2code.springboot.cruddemo.rest;

import com.lova2code.springboot.cruddemo.entity.Person;
import com.lova2code.springboot.cruddemo.service.StarWarsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StarWarsController {

    private final StarWarsService starWarsService;
    @GetMapping("/starwars")
    public Person getPerson() {
        return starWarsService.findActor();
    }




}
