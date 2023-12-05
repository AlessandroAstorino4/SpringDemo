package com.lova2code.springboot.cruddemo.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cards")
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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
