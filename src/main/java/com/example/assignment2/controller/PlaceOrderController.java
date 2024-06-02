package com.example.assignment2.controller;

import com.example.assignment2.model.*;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class PlaceOrderController {

    @FXML
    private TextField burritoQuantity; // TextField for burrito quantity input

    @FXML
    private TextField friesQuantity; // TextField for fries quantity input

    @FXML
    private TextField sodaQuantity; // TextField for soda quantity input

    @FXML
    private ListView<String> basketListView; // ListView to display items in the basket

    @FXML
    private Label totalPriceLabel; // Label to display the total price

    @FXML
    private Label waitingTimeLabel; // Label to display the waiting time

    private ObservableList<String> basketItems; // Observable list for basket items
    private Order order; // Order object for the current order
    private User currentUser; // User object for the current user
    private boolean isMealOrder = false; // Flag to indicate if the order is a meal

    // Initialize the controller
    public void initialize() {
        basketItems = FXCollections.observableArrayList();
        basketListView.setItems(basketItems);
        order = new Order();
    }

    // Set the current user
    public void setUser(User user) {
        this.currentUser = user;
    }

    // Set the order type to meal
    public void setMealOrder(boolean isMealOrder) {
        this.isMealOrder = isMealOrder;
        if (isMealOrder) {
            if (!currentUser.isVIP()) {
                showAlert("VIP Only", "Only VIP members can order meals.");
                return;
            }
            order.addFoodItem(new Meal(14.5, 1)); // Assuming Meal price without discount
            basketItems.add("Meal x 1");
            updateTotalPriceAndTime();
        }
    }

    // Handle adding items to the basket
    @FXML
    private void handleAddToBasket(ActionEvent event) {
        int burritoQty = getQuantity(burritoQuantity);
        int friesQty = getQuantity(friesQuantity);
        int sodaQty = getQuantity(sodaQuantity);

        if (burritoQty > 0) {
            order.addFoodItem(new Burrito(7.0, burritoQty));
            basketItems.add("Burrito x " + burritoQty);
        }
        if (friesQty > 0) {
            order.addFoodItem(new Fries(4.0, friesQty));
            basketItems.add("Fries x " + friesQty);
        }
        if (sodaQty > 0) {
            order.addFoodItem(new Soda(2.5, sodaQty));
            basketItems.add("Soda x " + sodaQty);
        }

        clearInputFields();
        updateTotalPriceAndTime();
    }

    // Handle removing selected items from the basket
    @FXML
    private void handleRemoveSelected(ActionEvent event) {
        String selectedItem = basketListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            basketItems.remove(selectedItem);
            FoodItem itemToRemove = order.findItemByDescription(selectedItem);
            if (itemToRemove != null) {
                order.removeFoodItem(itemToRemove);
            }
            updateTotalPriceAndTime();
        }
    }

    // Update the total price and waiting time
    private void updateTotalPriceAndTime() {
        double totalPrice = order.getTotalPrice();
        if (currentUser.isVIP() && isMealOrder) {
            totalPrice -= 3.0; // Apply VIP discount for meal orders
        }
        totalPriceLabel.setText("Total Price: $" + totalPrice);
        double waitingTime = order.getPrepTime(new Restaurant("Burrito King"));
        waitingTimeLabel.setText("Waiting Time: " + waitingTime + " minutes");
    }

    // Handle proceeding to payment
    @FXML
    private void handleProceedToPayment(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/payment.fxml"));
            Parent root = fxmlLoader.load();
            PaymentController paymentController = fxmlLoader.getController();
            paymentController.setOrder(order);
            paymentController.setUser(currentUser);
            paymentController.setTotalPrice(order.getTotalPrice()); // Set total price in payment controller
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle navigating back to the dashboard
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

    // Get the quantity from a TextField
    private int getQuantity(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Clear input fields
    private void clearInputFields() {
        burritoQuantity.clear();
        friesQuantity.clear();
        sodaQuantity.clear();
    }

    // Show an alert message
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
