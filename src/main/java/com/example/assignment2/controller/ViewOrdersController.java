package com.example.assignment2.controller;

import com.example.assignment2.model.Order;
import com.example.assignment2.model.User;
import com.example.assignment2.util.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Collections;
import java.util.List;

public class ViewOrdersController {

    @FXML
    private ListView<String> ordersListView;

    private User currentUser;
    private ObservableList<String> ordersList;

    public void initialize() {
        ordersList = FXCollections.observableArrayList();
        ordersListView.setItems(ordersList);
    }

    public void setUser(User user) {
        this.currentUser = user;
        loadOrders();
    }

    private void loadOrders() {
        List<Order> orders = UserData.getAllOrders(currentUser.getUsername());
        Collections.reverse(orders);
        for (Order order : orders) {
            ordersList.add(order.toString());
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
}
