package com.example.assignment2.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements Serializable {
    private LinkedList<FoodItem> items;
    private static final AtomicInteger orderCounter = new AtomicInteger(0);
    private int orderNumber;
    private LocalDateTime orderTime;
    private LocalDateTime collectionTime;
    private String status;
    private double finalPrice; // Store the actual price paid

    public Order() {
        items = new LinkedList<>();
        this.orderNumber = generateOrderNumber();
        this.orderTime = LocalDateTime.now();
        this.status = "await for collection";
        this.finalPrice = 0.0;
    }


    public void addFoodItem(FoodItem newItem) {
        for (FoodItem item : items) {
            if (item.getClass().getName().equals(newItem.getClass().getName())) {
                item.addQuantity(newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    public void removeFoodItem(FoodItem itemToRemove) {
        items.remove(itemToRemove);
    }

    public LinkedList<FoodItem> getItems() {
        return this.items;
    }

    public double getTotalPrice() {
        double sum = 0.0;
        for (FoodItem item : items) {
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public double getPrepTime(Restaurant restaurant) {
        if (restaurant == null) {
            return 0;
        }
        HashMap<String, Integer> cookables = this.mapToCookables();
        double cookTimeForBurritos = new Burrito(Restaurant.getPrice("Burrito"), cookables.get("Burritos")).getPreparationTime(restaurant);
        double cookTimeForFries = new Fries(Restaurant.getPrice("Fries"), cookables.get("Fries")).getPreparationTime(restaurant);
        return Math.max(cookTimeForFries, cookTimeForBurritos);
    }

    public HashMap<String, Integer> mapToCookables() {
        int numOfBurritos = 0;
        int numOfFries = 0;
        for (FoodItem item : items) {
            if (item instanceof Burrito) {
                numOfBurritos += item.getQuantity();
            } else if (item instanceof Fries) {
                numOfFries += item.getQuantity();
            } else if (item instanceof Meal) {
                numOfBurritos += item.getQuantity();
                numOfFries += item.getQuantity();
            }
        }
        HashMap<String, Integer> mapped = new HashMap<>();
        mapped.put("Burritos", numOfBurritos);
        mapped.put("Fries", numOfFries);
        return mapped;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Number: ").append(orderNumber).append("\n");
        sb.append("Order Time: ").append(orderTime).append("\n");
        if (collectionTime != null) {
            sb.append("Collection Time: ").append(collectionTime).append("\n");
        }
        sb.append("Items:\n");
        for (FoodItem item : items) {
            sb.append("   ").append(item.toString()).append("\n");
        }
        sb.append("Total Price: $").append(getTotalPrice()).append("\n");
        sb.append("Status: ").append(status);
        return sb.toString();
    }

    public FoodItem findItemByDescription(String description) {
        for (FoodItem item : items) {
            if (description.contains(item.getClass().getSimpleName()) && description.contains(String.valueOf(item.getQuantity()))) {
                return item;
            }
        }
        return null;
    }
    public void addMeal() {
        Burrito burrito = new Burrito(Restaurant.getPrice("Burrito"), 1);
        Fries fries = new Fries(Restaurant.getPrice("Fries"), 1);
        Soda soda = new Soda(Restaurant.getPrice("Soda"), 1);
        addFoodItem(burrito);
        addFoodItem(fries);
        addFoodItem(soda);
    }
    public double applyVIPDiscount() {
        double totalPrice = getTotalPrice();
        return Math.max(totalPrice - 3, 0); // Apply a discount of $3
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public double getTotalPrice(boolean isVIP) {
        double sum = 0.0;
        for (FoodItem item : items) {
            sum += item.getTotalPrice();
        }
        if (isVIP) {
            sum -= 3;  // VIP discount
        }
        return sum;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public static int generateOrderNumber() {
        return orderCounter.incrementAndGet();
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(LocalDateTime collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isCancellable() {
        return "awaiting collection".equals(status);
    }

    public void cancel() {
        if (isCancellable()) {
            status = "cancelled";
        }
    }
}
