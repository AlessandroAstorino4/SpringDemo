package com.lova2code.springboot.cruddemo.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class ResponseStatus {

    private boolean enabled;

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "enabled=" + enabled +
                '}';
    }
}
