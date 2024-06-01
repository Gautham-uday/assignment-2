package com.example.assignment2.model;

import java.io.Serializable;

public class Burrito extends FoodItem implements Cookable, Serializable {
    private static final int batchPrepTime = 9;
    private static final int batchSize = 2;

    public Burrito(double price, int quantity) {
        super(price, quantity);
    }

    @Override
    public int getPreparationTime(Restaurant restaurant) {
        return batchPrepTime * ((int) Math.ceil(this.getQuantity() / ((double)batchSize)));
    }

    @Override
    public int getActualQuantityCooked(Restaurant restaurant) {
        return this.getQuantity();
    }

    @Override
    public String toString() {
        return "Burrito (Quantity: " + getQuantity() + ", Price: $" + getUnitPrice() + ")";
    }
}
