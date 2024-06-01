package com.example.assignment2.model;

import java.io.Serializable;

public class Soda extends FoodItem implements Serializable {
    public Soda(double price, int quantity) {
        super(price, quantity);
    }

    @Override
    public String toString() {
        return "Soda (Quantity: " + getQuantity() + ", Price: $" + getUnitPrice() + ")";
    }
}
