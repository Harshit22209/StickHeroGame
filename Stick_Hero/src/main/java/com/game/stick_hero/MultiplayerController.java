package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.StickHero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MultiplayerController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;


    static void showOtherPlayers(Stage stage,StickHero stickHero){
        Group root=new Group();
        Scene scene=new Scene(root,600,400);
        Text nameHead=new Text();
        double HeadFont=25;
        double font=20;
        nameHead.setText("Name");
        nameHead.setX(10);
        nameHead.setY(50);
        nameHead.setFont(Font.font(HeadFont));
        Button back=new Button();
        back.setText("Back");
        back.setLayoutX(250);
        back.setLayoutY(5);
        back.setPrefSize(100, 20);
        back.setOnAction(actionEvent -> {

            FXMLLoader loader = new FXMLLoader(MultiplayerController.class.getResource("GameMenu.fxml"));
            Parent root2=null;
            try {
                root2= loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //userName.setText(name.getText());
            GameController controllerB = loader.getController();

            // Create an object to pass to ControllerB

            controllerB.receiveData(stickHero);

            // Stage stage = new Stage();
            stage.setScene(new Scene(root2));
            stage.show();
        });
        Text idHead=new Text();
        idHead.setText("Id");
        idHead.setX(100);
        idHead.setY(50);
        idHead.setFont(Font.font(HeadFont));
        Text scoreHead=new Text();
        scoreHead.setText("Best Score");
        scoreHead.setX(200);
        scoreHead.setY(50);
        scoreHead.setFont(Font.font(HeadFont));
        Text ActionHead =new Text();
        ActionHead.setText("Action");
        ActionHead.setX(400);
        ActionHead.setY(50);
        ActionHead.setFont(Font.font(HeadFont));
        ArrayList<StickHero> heros=DataBaseService.getAllPlayers();
        int y=80;
        for(StickHero hero:heros){
            Text name=new Text(hero.getName());
            name.setX(10);
            name.setY(y);
            name.setFont(Font.font(font));
            Text Id=new Text(hero.getId());
            Id.setX(100);
            Id.setY(y);
            Id.setFont(Font.font(font));
            Text score=new Text(hero.getMaxScore()+"");
            score.setX(200);
            score.setY(y);
            score.setFont(Font.font(font));
            Button view=new Button("view");
            view.setLayoutX(400);
            view.setLayoutY(y-20);
            view.setPrefSize(50,20);
            view.setOnAction(actionEvent -> {
                    showPlayer( stage,hero,stickHero);
            });

            root.getChildren().addAll(name,Id,score,view);

            y+=30;

        }
        root.getChildren().addAll(nameHead,idHead,scoreHead,back,ActionHead);
        stage.setScene(scene);
    }
    static void showPlayer(Stage stage,StickHero other,StickHero self ){
        Group root=new Group();
        Scene scene=new Scene(root,600,400);
        Text name=new Text();
        name.setText("Name:"+other.getName());
        name.setX(100);
        name.setY(50);
        name.setFont(Font.font("Verdana",20));
        Text Id=new Text();

        Id.setText("Id:"+other.getId());
        Id.setX(200);
        Id.setFont(Font.font("Verdana",20));
        Id.setY(50);


        Button back=new Button();
        back.setText("Back");
        back.setLayoutX(300);
        back.setLayoutY(30);
        back.setPrefSize(100, 40);
        back.setOnAction(actionEvent -> {

            FXMLLoader loader = new FXMLLoader(ProfileController.class.getResource("GameMenu.fxml"));
            Parent root2=null;
            try {
                root2= loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //userName.setText(name.getText());
            GameController controllerB = loader.getController();

            // Create an object to pass to ControllerB

            controllerB.receiveData(self);

            // Stage stage = new Stage();
            stage.setScene(new Scene(root2));
            stage.show();
        });
        int y=80;
        Text ScoreHead=new Text("Score");
        ScoreHead.setX();
        Text bestScoreHead=new Text("BestScore");
        Text ActionHead=new Text("Action");

        for(var game:other.getGamesPlayed()){
            System.out.println("Hello");
            Text score=new Text();
            score.setText(game.getScore()+"");
            score.setX(30);
            score.setY(y);
            Text bestScore=new Text();
            score.setText(game.getbestScore()+"");
            score.setX(30);
            score.setY(y);

            score.setFont(Font.font("Verdana",20));
            y+=30;
            Button view=new Button("play");
            view.setLayoutX(400);
            view.setLayoutY(y-20);
            view.setPrefSize(50,20);
            view.setOnAction(actionEvent -> {

            });

            root.getChildren().add(score);
        }
        root.getChildren().addAll(name,Idback);
        stage.setScene(scene);
    }


}
