package com.example.assignment2.controller;

import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VipUpgradeController {

    @FXML
    private TextField emailField; // TextField for entering email

    private User currentUser; // The current logged-in user

    // Method to set the current user
    public void setUser(User user) {
        this.currentUser = user;
    }

    // Handle the action to upgrade the user to VIP
    @FXML
    private void handleUpgradeToVIP() {
        String email = emailField.getText();

        // Validate the email address
        if (email.isEmpty() || !email.contains("@")) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Set the user as VIP and update their email
        currentUser.setVIP(true);
        currentUser.setEmail(email);
        UserData.updateUser(currentUser);

        // Show success alert
        showAlert("Upgrade Successful", "You are now a VIP user. Please log out and log in again to access VIP functionalities.");

        // Close the current stage (window)
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    // Handle the action to cancel the upgrade
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    // Method to show an alert with a specified title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
