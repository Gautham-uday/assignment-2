package com.example.assignment2.model;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isVIP;
    private String email;
    private int credits;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isVIP = false;
        this.credits = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredits() {
        return credits;
    }

    public void addCredits(int amount) {
        this.credits += amount;
    }

    public void deductCredits(int amount) {
        this.credits -= amount;
    }


}
