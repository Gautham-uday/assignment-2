package com.example.assignment2.controller;

import com.example.assignment2.model.Order;
import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentController {

    @FXML
    private TextField cardNumberField;  // TextField for card number input

    @FXML
    private TextField expiryDateField;  // TextField for card expiry date input

    @FXML
    private TextField cvvField;  // TextField for CVV input

    @FXML
    private Label totalPriceLabel;  // Label to display total price

    private Order order;  // Order object for the current order
    private User currentUser;  // User object for the current user
    private double totalPrice;  // Total price of the order

    // Method to set the order object
    public void setOrder(Order order) {
        this.order = order;
    }

    // Method to set the user object
    public void setUser(User user) {
        this.currentUser = user;
    }

    // Method to set and display the total price
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        totalPriceLabel.setText("Total Price: $" + totalPrice);
    }

    // Method to handle the confirmation of payment
    @FXML
    private void handleConfirmPayment(ActionEvent event) {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        // Validate card number, expiry date, and CVV
        if (validateCardNumber(cardNumber) && validateExpiryDate(expiryDate) && validateCVV(cvv)) {
            // Process payment (assuming success)
            order.setOrderNumber(Order.generateOrderNumber());
            UserData.addOrder(currentUser.getUsername(), order);
            // Add credits for the user
            currentUser.addCredits((int) totalPrice);

            showAlert("Payment Successful", "Your order has been placed successfully.");
            handleBackToDashboard(event);
        }
    }

    // Method to handle cancellation of payment
    @FXML
    private void handleCancel(ActionEvent event) {
        handleBackToDashboard(event);
    }

    // Method to validate card number
    private boolean validateCardNumber(String cardNumber) {
        if (cardNumber.length() == 16 && cardNumber.matches("\\d+")) {
            return true;
        } else {
            showAlert("Invalid Card Number", "Card number must be 16 digits.");
            return false;
        }
    }

    // Method to validate expiry date
    private boolean validateExpiryDate(String expiryDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate date = LocalDate.parse("01/" + expiryDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            if (date.isAfter(LocalDate.now())) {
                return true;
            } else {
                showAlert("Invalid Expiry Date", "Expiry date must be a future date.");
                return false;
            }
        } catch (DateTimeParseException e) {
            showAlert("Invalid Expiry Date", "Expiry date format should be MM/yy.");
            return false;
        }
    }

    // Method to validate CVV
    private boolean validateCVV(String cvv) {
        if (cvv.length() == 3 && cvv.matches("\\d+")) {
            return true;
        } else {
            showAlert("Invalid CVV", "CVV must be 3 digits.");
            return false;
        }
    }

    // Method to handle navigation back to the dashboard
    @FXML
    private void handleBackToDashboard(ActionEvent event) {
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

    // Method to display alert messages
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
