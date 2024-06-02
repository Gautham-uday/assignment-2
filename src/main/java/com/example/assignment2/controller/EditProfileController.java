package com.example.assignment2.controller;

import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class EditProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    private User currentUser;

    // Set the current user and populate the fields with user's data
    public void setUser(User user) {
        this.currentUser = user;
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
    }

    // Handle the save action when the user updates their profile
    @FXML
    private void handleSave(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();

        // Update the current user's details
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setPassword(password);

        // Save the updated user data
        UserData.updateUser(currentUser);
        showAlert("Success", "Profile updated successfully.");

        // Close the edit profile window
        closeWindow();
    }

    // Handle the cancel action to close the window without saving changes
    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    // Close the current window
    private void closeWindow() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
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
