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
    private TextField burritoQuantity;

    @FXML
    private TextField friesQuantity;

    @FXML
    private TextField sodaQuantity;

    @FXML
    private ListView<String> basketListView;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label waitingTimeLabel;

    private ObservableList<String> basketItems;
    private Order order;
    private User currentUser;

    public void initialize() {
        basketItems = FXCollections.observableArrayList();
        basketListView.setItems(basketItems);
        order = new Order();
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

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

    private void updateTotalPriceAndTime() {
        double totalPrice = order.getTotalPrice();
        totalPriceLabel.setText("Total Price: $" + totalPrice);
        double waitingTime = order.getPrepTime(new Restaurant("Burrito King"));
        waitingTimeLabel.setText("Waiting Time: " + waitingTime + " minutes");
    }

    @FXML
    private void handleProceedToPayment(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/payment.fxml"));
            Parent root = fxmlLoader.load();
            PaymentController paymentController = fxmlLoader.getController();
            paymentController.setOrder(order);
            paymentController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private int getQuantity(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void clearInputFields() {
        burritoQuantity.clear();
        friesQuantity.clear();
        sodaQuantity.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
