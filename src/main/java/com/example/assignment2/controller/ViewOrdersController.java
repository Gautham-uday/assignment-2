package com.example.assignment2.controller;

import com.example.assignment2.model.Order;
import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Collections;
import java.util.List;

public class ViewOrdersController {

    @FXML
    private ListView<String> ordersListView; // ListView to display the orders

    private User currentUser; // The current logged-in user
    private ObservableList<String> ordersList; // ObservableList to hold the orders

    // Initialize the controller
    public void initialize() {
        ordersList = FXCollections.observableArrayList();
        ordersListView.setItems(ordersList);
    }

    // Set the current user and load their orders
    public void setUser(User user) {
        this.currentUser = user;
        loadOrders(); // Load orders for the current user
    }

    // Load orders for the current user and display them in the ListView
    private void loadOrders() {
        List<Order> orders = UserData.getAllOrders(currentUser.getUsername());
        Collections.reverse(orders); // Reverse the order to display the latest orders first
        for (Order order : orders) {
            ordersList.add(order.toString());
        }
    }

    // Handle the action to go back to the main dashboard
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/main.fxml"));
            Parent root = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.setUser(currentUser); // Pass the current user back to the main controller
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root)); // Set the scene to the main dashboard
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
