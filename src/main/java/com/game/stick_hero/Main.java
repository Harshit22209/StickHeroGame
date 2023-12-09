package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.GameDetail;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
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
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("./Local"), "*.ser")) {
            for (Path filePath : directoryStream) {
                // Read the content of each text file
                String userId="";
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
                    GameDetail game=(GameDetail) ois.readObject();
                    userId=game.getUserId();
                    DataBaseService.addGameToUser(game.getUserId(),game.getId(), game.getScore());
                    DataBaseService.updateScoreOfUser(game.getUserId(),game.getScore());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    Files.delete(Paths.get("./Local/"+userId+".ser"));
                    System.out.println("Deleted");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        launch();
    }

}