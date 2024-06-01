package com.example.assignment2.controller;

import com.example.assignment2.model.Restaurant;
import com.example.assignment2.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class UpdatePricesController {

    @FXML
    private TextField burritoPrice;

    @FXML
    private TextField friesPrice;

    @FXML
    private TextField sodaPrice;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleUpdatePrices(ActionEvent event) {
        double burritoPriceVal = Double.parseDouble(burritoPrice.getText());
        double friesPriceVal = Double.parseDouble(friesPrice.getText());
        double sodaPriceVal = Double.parseDouble(sodaPrice.getText());

        Restaurant.updatePrice("Burrito", burritoPriceVal);
        Restaurant.updatePrice("Fries", friesPriceVal);
        Restaurant.updatePrice("Soda", sodaPriceVal);

        showAlert("Prices Updated", "Food item prices have been updated successfully.");

        handleBack(event);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/main.fxml"));
            Parent root = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
