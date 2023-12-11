package com.lova2code.springboot.cruddemo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "cards")
@Data
public class Card {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "pan")
    private String pan;

    @Column(name = "expire")
    private long expire;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "enabled")
    private boolean enabled;

    public Card() {}

    public Card(String token, String pan, long expire, String cvv, boolean enabled) {
        this.token = token;
        this.pan = pan;
        this.expire = expire;
        this.cvv = cvv;
        this.enabled = enabled;
    }


    @Override
    public String toString() {
        return "Card{" +
                "token=" + token +
                ", pan='" + pan + '\'' +
                ", expireDate=" + expire +
                ", cvv='" + cvv + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
