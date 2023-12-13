package com.lova2code.springboot.cruddemo.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class MicroserviceProperties {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${backend-path}")
    private String backendPath;

}
