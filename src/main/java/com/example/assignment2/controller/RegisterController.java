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

public class RegisterController {

    @FXML
    private TextField firstNameField; // TextField for entering the first name

    @FXML
    private TextField lastNameField; // TextField for entering the last name

    @FXML
    private TextField usernameField; // TextField for entering the username

    @FXML
    private PasswordField passwordField; // PasswordField for entering the password

    // Handle the registration process
    @FXML
    private void handleRegister(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if the username already exists
        if (!UserData.isUserExists(username)) {
            // Add the new user to the user data
            UserData.addUser(new User(firstName, lastName, username, password));
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("User registered successfully.");
            alert.showAndWait();
            showLogin(event); // Navigate to the login screen
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists.");
            alert.showAndWait();
        }
    }

    // Show the login screen
    @FXML
    private void showLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
