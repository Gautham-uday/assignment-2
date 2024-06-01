package com.example.assignment2.util;

import com.example.assignment2.model.User;
import com.example.assignment2.model.Order;

import java.io.*;
import java.util.*;

public class UserData implements Serializable {
    private static final String FILE_PATH = "users.dat";
    private static final String ORDERS_FILE_PATH = "orders.dat";
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, List<Order>> orders = new HashMap<>();

    static {
        loadUsers();
        loadOrders();
    }

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
        System.out.println("User added: " + user.getUsername());
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean isUserExists(String username) {
        return users.containsKey(username);
    }

    public static boolean validateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public static void addOrder(String username, Order order) {
        orders.computeIfAbsent(username, k -> new ArrayList<>()).add(order);
        saveOrders();
        System.out.println("Order added for user: " + username + ", Order number: " + order.getOrderNumber());
    }

    public static List<Order> getActiveOrders(String username) {
        List<Order> userOrders = orders.getOrDefault(username, new ArrayList<>());
        List<Order> activeOrders = new ArrayList<>();
        for (Order order : userOrders) {
            if ("await for collection".equals(order.getStatus())) {
                activeOrders.add(order);
            }
        }
        return activeOrders;
    }

    public static List<Order> getAllOrders(String username) {
        return orders.getOrDefault(username, new ArrayList<>());
    }

    public static void updateOrder(String username, Order order) {
        List<Order> userOrders = orders.get(username);
        if (userOrders != null) {
            for (int i = 0; i < userOrders.size(); i++) {
                if (userOrders.get(i).getOrderNumber() == order.getOrderNumber()) {
                    userOrders.set(i, order);
                    break;
                }
            }
            saveOrders();
        }
    }


    public static void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (Map<String, User>) in.readObject();
            System.out.println("Users loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            users = new HashMap<>();
            System.out.println("No existing users found.");
        }
    }

    public static void updateUser(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
        System.out.println("User updated: " + user.getUsername());
    }

    public static void saveOrders() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE_PATH))) {
            out.writeObject(orders);
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadOrders() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ORDERS_FILE_PATH))) {
            orders = (Map<String, List<Order>>) in.readObject();
            System.out.println("Orders loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            orders = new HashMap<>();
            System.out.println("No existing orders found.");
        }
    }
}


