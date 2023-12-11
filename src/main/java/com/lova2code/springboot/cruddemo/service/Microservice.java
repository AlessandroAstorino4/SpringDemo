package com.lova2code.springboot.cruddemo.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class Microservice {

    @Value("${server.servlet.context-path}")
    private String contextPath;

}
