package com.example.assignment2.controller;

import com.example.assignment2.model.Order;
import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportOrdersController {

    @FXML
    private ListView<CheckBox> ordersListView;

    @FXML
    private CheckBox includeItems;

    @FXML
    private CheckBox includePrice;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        loadOrders(user);
    }

    private void loadOrders(User user) {
        List<Order> allOrders = UserData.getAllOrders(user.getUsername());
        for (Order order : allOrders) {
            CheckBox checkBox = new CheckBox(order.toString());
            checkBox.setUserData(order);
            ordersListView.getItems().add(checkBox);
        }
    }

    @FXML
    private void handleExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Orders");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                for (CheckBox checkBox : ordersListView.getItems()) {
                    if (checkBox.isSelected()) {
                        Order order = (Order) checkBox.getUserData();
                        writer.write(exportOrder(order));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String exportOrder(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Number: ").append(order.getOrderNumber()).append(",");
        sb.append("Order Time: ").append(order.getOrderTime()).append(",");
        if (includeItems.isSelected()) {
            sb.append("Items: ").append(order.getItems()).append(",");
        }
        if (includePrice.isSelected()) {
            sb.append("Total Price: ").append(order.getTotalPrice()).append(",");
        }
        sb.append("Status: ").append(order.getStatus()).append("\n");
        return sb.toString();
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/main.fxml"));
            Parent root = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.setUser(currentUser);  // Ensure the user is passed back to the dashboard
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
