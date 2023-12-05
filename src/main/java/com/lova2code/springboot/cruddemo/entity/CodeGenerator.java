package com.lova2code.springboot.cruddemo.entity;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeGenerator {

    public String generateRandomNumericString(int length) {
        String numericChars = "0123456789";
        return generateRandomString(numericChars, length);
    }

    public String generateRandomAlphaNumericString(int lenght) {
        String alphaNumericChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return generateRandomString(alphaNumericChars, lenght);
    }

    public String generateRandomString(String characters, int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }


}
