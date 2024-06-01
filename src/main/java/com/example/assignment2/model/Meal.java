package com.example.assignment2.model;

import java.io.Serializable;

public class Meal extends FoodItem implements Serializable {
    public Meal(double price, int quantity) {
        super(price, quantity);
    }

    @Override
    public String toString() {
        return "Meal (Quantity: " + getQuantity() + ", Price: $" + getUnitPrice() + ")";
    }
}
