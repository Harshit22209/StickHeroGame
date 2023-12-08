package com.game.stick_hero;

import com.mongodb.client.MongoClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("Welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Stick Hero");
        stage.setOnCloseRequest(event->{
            stage.close();
            DataBaseService.mongoClient.close();
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DataBaseService.connect();
        launch();
    }

}