package com.lova2code.springboot.cruddemo.service;

import com.lova2code.springboot.cruddemo.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class StarWarsServiceImpl implements StarWarsService {

    private final RestTemplate restTemplate;
    @Override
    public Person findActor() {
        String SWAPI_BASE_URL = "https://swapi.dev/api/people/1";
        return restTemplate.getForObject(SWAPI_BASE_URL, Person.class);
    }
}
