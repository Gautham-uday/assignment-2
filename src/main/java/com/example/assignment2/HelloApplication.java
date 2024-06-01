package com.example.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.assignment2.util.UserData;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/assignment2/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Burrito King Restaurant");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Ensure data is saved when the application stops
        UserData.saveUsers();
        UserData.saveOrders();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
