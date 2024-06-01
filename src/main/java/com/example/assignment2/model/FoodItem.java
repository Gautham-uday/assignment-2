package com.example.assignment2.model;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private double unitPrice;
    private int quantity;

    public FoodItem(double price, int quantity) {
        this.unitPrice = price;
        this.quantity = quantity;
    }

    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void addQuantity(int count) { this.quantity += count; }
    public double getTotalPrice() { return unitPrice * quantity; }
}
