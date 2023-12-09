package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MultiplayerController {

    private static MultiplayerGamePlay multiplayer;

private static StickHeroGame stickHeroGame;
private static int cnt;
//strategy
//
    static void showOtherPlayers(Stage stage,StickHero stickHero){
        stickHeroGame=new StickHeroGame(stickHero);
        Group root=new Group();
        ScrollPane scrollPane = new ScrollPane(root);


        Scene scene=new Scene(scrollPane,600,400);
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

            GameController controllerB = loader.getController();



            controllerB.receiveStickHero(stickHero);


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
            if(hero.getId().equals(stickHero.getId())){
                continue;
            }
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
        ScrollPane scrollPane = new ScrollPane(root);



        Scene scene=new Scene(scrollPane,600,400);
        Text name=new Text();
        name.setText("Name:"+other.getName());
        name.setX(100);
        name.setY(50);
        name.setFont(Font.font("Verdana",20));
        Text Id=new Text();

        Id.setText("Id:"+other.getId());
        Id.setX(250);
        Id.setFont(Font.font("Verdana",20));
        Id.setY(50);


        Button back=new Button();
        back.setText("Back");
        back.setLayoutX(400);
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
            GameController controllerB = loader.getController();


            controllerB.receiveStickHero(self);

            stage.setScene(new Scene(root2));
            stage.show();
        });
        int y=100;
        Text scoreHead=(Text) Components.getComponent(Components.Componenet.Text,30,y,"Score",20,0);
        Text bestScoreHead=(Text) Components.getComponent(Components.Componenet.Text,100,y, "Best Score", 20,0);
        Text action=(Text) Components.getComponent(Components.Componenet.Text,300,y,"Action", 20,0);
        y+=40;
        for(var game:other.getGamesPlayed()){
            System.out.println("Hello");
            boolean isPlayed;
            if(self.hasPlayed(game.getId())){
                isPlayed=true;
            } else {
                isPlayed = false;
            }
            Text score=(Text) Components.getComponent(Components.Componenet.Text,30,y,game.getScore()+"",20,0);
            Text bestScore=(Text) Components.getComponent(Components.Componenet.Text,100,y, game.getbestScore()+"", 20,0);
            Button play=(Button) Components.getComponent(Components.Componenet.Button,250,y-20,isPlayed?"played":"play",70,20);
            Button leaderboard=(Button) Components.getComponent(Components.Componenet.Button,330,y-20,"leaderBoard",100,20);
            play.setOnAction(actionEvent -> {
                if(!isPlayed) {

                    multiplayerPlay(self, game, stage);
                }
            });
            leaderboard.setOnAction(actionEvent -> {
                    stage.setScene(createScene(game.getId(),stage,self));
            });

            y+=30;
            root.getChildren().addAll(score,bestScore,play,leaderboard);
        }
        root.getChildren().addAll(name,Id,back,scoreHead,bestScoreHead,action);
        stage.setScene(scene);
    }
    static void multiplayerPlay(StickHero stickHero, GameDetail game, Stage stage){
        StickHeroScene sc=game.getScenes().get(0);
        cnt=-1;
        stickHeroGame.startGame();
        game.setUserId(stickHero.getId());
        game.setCherryCollected(0);

        Random random=new Random();
        multiplayer=new MultiplayerGamePlay(stickHeroGame,stickHero,stage,game,"bg"+random.nextInt(1,4)+".jpeg");
        multiplayer.playGame();
    }

    private static Scene createScene(String gameId,Stage stage,StickHero self) {
        Group root = new Group();
        ScrollPane scrollPane = new ScrollPane(root);

        Scene scene=new Scene(scrollPane,600,400);
        Button back=(Button) Components.getComponent(Components.Componenet.Button,230,20,"Return",150,30);
        back.setOnAction(actionEvent -> {

            FXMLLoader loader = new FXMLLoader(ProfileController.class.getResource("GameMenu.fxml"));
            Parent root2=null;
            try {
                root2= loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GameController controllerB = loader.getController();

            controllerB.receiveStickHero(self);

            stage.setScene(new Scene(root2));
            stage.show();
        });
        Text heading = (Text) Components.getComponent(Components.Componenet.Text, 250, 80, "LeaderBoard", 30, 0);
       int y=130;
        Text idHead = (Text) Components.getComponent(Components.Componenet.Text, 5, y, "id", 30, 0);
        Text nameHead = (Text) Components.getComponent(Components.Componenet.Text, 150, y, "name", 30, 0);
        Text scoreHead = (Text) Components.getComponent(Components.Componenet.Text, 300, y, "score", 30, 0);

        ArrayList<String[]> players = DataBaseService.getAllPlayersPlayedGame(gameId);
        y+=30;
        root.getChildren().addAll(heading,idHead,nameHead,scoreHead,back);
        for (var player : players) {

            Text id = (Text) Components.getComponent(Components.Componenet.Text, 5, y, player[0], 20, 0);
            Text name = (Text) Components.getComponent(Components.Componenet.Text, 150, y, player[1], 20, 0);
            Text score = (Text) Components.getComponent(Components.Componenet.Text, 300, y, player[2], 20, 0);
            y += 30;
            root.getChildren().addAll(id,name,score);

        }

       return scene;

    }


}
