package com.example.assignment2.controller;

import com.example.assignment2.model.Order;
import com.example.assignment2.model.Restaurant;
import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CollectOrderController {

    @FXML
    private TextField orderNumberField;

    @FXML
    private TextField collectionTimeField;

    private User currentUser;
    private Order currentOrder;
    private Restaurant restaurant;

    // Set the current user
    public void setUser(User user) {
        this.currentUser = user;
    }

    // Set the current order and display the order number
    public void setOrder(Order order) {
        this.currentOrder = order;
        orderNumberField.setText(String.valueOf(order.getOrderNumber()));
    }

    // Set the restaurant instance
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // Handle the confirmation of order collection
    @FXML
    private void handleConfirmCollection(ActionEvent event) {
        String collectionTimeStr = collectionTimeField.getText();
        LocalDateTime collectionTime;
        try {
            // Parse the collection time input
            collectionTime = LocalDateTime.parse(collectionTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            showAlert("Invalid Time Format", "Please enter the collection time in the format YYYY-MM-DDTHH:MM.");
            return;
        }

        // Ensure the collection time is valid
        if (collectionTime.isBefore(currentOrder.getOrderTime().plusMinutes((long) currentOrder.getPrepTime(restaurant)))) {
            showAlert("Invalid Collection Time", "Collection time must be at least order-placed time + time to prepare.");
            return;
        }

        // Ensure the order status is 'await for collection'
        if (!"await for collection".equals(currentOrder.getStatus())) {
            showAlert("Invalid Order Status", "Only orders that are 'await for collection' can be collected.");
            return;
        }

        // Update the order status and collection time
        currentOrder.setCollectionTime(collectionTime);
        currentOrder.setStatus("collected");

        // Save the updated orders to the file
        UserData.saveOrders();

        showAlert("Order Collected", "The order has been collected successfully.");

        // Go back to the main dashboard
        handleBack(event);
    }

    // Handle the action to go back to the main dashboard
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/main.fxml"));
            Parent root = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show an alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
