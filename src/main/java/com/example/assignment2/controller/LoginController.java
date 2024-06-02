package com.example.assignment2.controller;

import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class LoginController {
    @FXML
    private TextField usernameField; // TextField for entering the username
    @FXML
    private PasswordField passwordField; // PasswordField for entering the password

    // Handles the login action
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate the user credentials
        if (isValidUser(username, password)) {
            try {
                // Load the main view
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/main.fxml"));
                Parent root = fxmlLoader.load();

                // Set the current user in the main controller
                MainController mainController = fxmlLoader.getController();
                mainController.setUser(UserData.getUser(username));

                // Change the scene to the main view
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Show an error alert if the login fails
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    // Handles showing the registration view
    @FXML
    private void showRegister(ActionEvent event) {
        try {
            // Load the registration view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/register.fxml"));
            Parent root = fxmlLoader.load();

            // Change the scene to the registration view
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Validates the user credentials
    private boolean isValidUser(String username, String password) {
        User user = UserData.getUser(username);
        return user != null && user.getPassword().equals(password);
    }
}
