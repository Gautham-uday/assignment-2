package com.example.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.assignment2.util.UserData;

public class Main extends Application {

    // Entry point for the JavaFX application
    @Override
    public void start(Stage stage) throws Exception {
        // Load the login view from the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        // Set the title of the main window
        stage.setTitle("Burrito King Restaurant");

        // Set the scene for the main window
        stage.setScene(scene);

        // Display the main window
        stage.show();
    }

    // Method called when the application stops
    @Override
    public void stop() {
        // Ensure user data is saved when the application stops
        UserData.saveUsers();

        // Ensure order data is saved when the application stops
        UserData.saveOrders();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
