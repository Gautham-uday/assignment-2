package com.example.assignment2.controller;

import com.example.assignment2.model.Order;
import com.example.assignment2.model.Restaurant;
import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.List;

public class MainController {

    @FXML
    private Label firstNameLabel; // Label to display the user's first name

    @FXML
    private Label lastNameLabel; // Label to display the user's last name

    @FXML
    private VBox ordersBox; // VBox to hold the list of active orders

    private User currentUser; // Variable to store the current user

    // Method to set the current user and update the UI with user information
    public void setUser(User user) {
        this.currentUser = user;
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        loadActiveOrders(user); // Load active orders for the user
    }

    // Method to load active orders for the user and display them in the UI
    private void loadActiveOrders(User user) {
        ordersBox.getChildren().clear(); // Clear the existing orders
        List<Order> activeOrders = UserData.getActiveOrders(user.getUsername());
        for (Order order : activeOrders) {
            HBox orderBox = new HBox(10);
            Label orderLabel = new Label(order.toString());
            Button cancelButton = new Button("Cancel");

            cancelButton.setDisable(!order.isCancellable()); // Disable cancel button if the order cannot be cancelled
            cancelButton.setOnAction(e -> handleCancelOrder(order)); // Set action for cancel button

            orderBox.getChildren().addAll(orderLabel, cancelButton); // Add order details and cancel button to HBox
            ordersBox.getChildren().add(orderBox); // Add HBox to VBox
        }
    }

    // Method to handle the cancellation of an order
    private void handleCancelOrder(Order order) {
        if (order.isCancellable()) {
            order.cancel();
            UserData.updateOrder(currentUser.getUsername(), order); // Update the order status in the data
            showAlert("Order Cancelled", "Your order has been cancelled successfully.");
            loadActiveOrders(currentUser); // Refresh the order list
        } else {
            showAlert("Cancellation Failed", "This order cannot be cancelled.");
        }
    }

    // Method to handle user logout
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle placing a new order
    @FXML
    private void handlePlaceOrder(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/placeOrder.fxml"));
            Parent root = fxmlLoader.load();
            PlaceOrderController placeOrderController = fxmlLoader.getController();
            placeOrderController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle ordering a meal for VIP users
    @FXML
    private void handleOrderMeal(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/placeOrder.fxml"));
            Parent root = fxmlLoader.load();
            PlaceOrderController placeOrderController = fxmlLoader.getController();
            placeOrderController.setUser(currentUser);
            placeOrderController.setMealOrder(true); // Assume this method sets the order type to meal
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle showing the sales report
    @FXML
    private void handleShowSalesReport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/salesReport.fxml"));
            Parent root = fxmlLoader.load();
            SalesReportController salesReportController = fxmlLoader.getController();
            salesReportController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle updating the prices
    @FXML
    private void handleUpdatePrices(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/updatePrices.fxml"));
            Parent root = fxmlLoader.load();
            UpdatePricesController updatePricesController = fxmlLoader.getController();
            updatePricesController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Update Prices view.");
        }
    }

    // Method to handle editing the user profile
    @FXML
    private void handleEditProfile(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/editProfile.fxml"));
            Parent root = fxmlLoader.load();
            EditProfileController editProfileController = fxmlLoader.getController();
            editProfileController.setUser(currentUser);
            Stage stage = new Stage();
            stage.setTitle("Edit Profile");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Edit Profile view.");
        }
    }

    // Method to handle viewing all orders
    @FXML
    private void handleViewOrders(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/viewOrders.fxml"));
            Parent root = fxmlLoader.load();
            ViewOrdersController viewOrdersController = fxmlLoader.getController();
            viewOrdersController.setUser(currentUser);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the View Orders view.");
        }
    }

    // Method to handle collecting an order
    @FXML
    private void handleCollectOrder(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/collectOrder.fxml"));
            Parent root = fxmlLoader.load();
            CollectOrderController collectOrderController = fxmlLoader.getController();
            collectOrderController.setUser(currentUser);

            // Retrieve the selected order to be collected (for simplicity, we'll take the first active order)
            List<Order> activeOrders = UserData.getActiveOrders(currentUser.getUsername());
            if (activeOrders.isEmpty()) {
                showAlert("No Active Orders", "There are no active orders to collect.");
                return;
            }
            Order orderToCollect = activeOrders.get(0);
            collectOrderController.setOrder(orderToCollect);

            // Pass the Restaurant instance to the CollectOrderController
            Restaurant restaurant = new Restaurant("Burrito King"); // Replace with the actual restaurant instance if available
            collectOrderController.setRestaurant(restaurant);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Collect Order view.");
        }
    }

    // Method to handle exporting orders
    @FXML
    private void handleExportOrders(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/exportOrders.fxml"));
            Parent root = fxmlLoader.load();
            ExportOrdersController exportOrdersController = fxmlLoader.getController();
            exportOrdersController.setUser(currentUser); // Ensure this method is correctly invoked
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Export Orders view.");
        }
    }

    // Method to handle upgrading to VIP
    @FXML
    private void handleUpgradeToVIP(ActionEvent event) {
        if (currentUser.isVIP()) {
            showAlert("Already a VIP", "You are already a VIP user.");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/vipUpgrade.fxml"));
            Parent root = fxmlLoader.load();
            VipUpgradeController vipUpgradeController = fxmlLoader.getController();
            vipUpgradeController.setUser(currentUser);
            Stage stage = new Stage();
            stage.setTitle("Upgrade to VIP");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the VIP Upgrade view.");
        }
    }

    // Method to display alerts
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
