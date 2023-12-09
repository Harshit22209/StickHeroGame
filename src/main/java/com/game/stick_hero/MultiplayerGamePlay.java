package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MultiplayerGamePlay extends GamePlay{

    private GameDetail game;
    public MultiplayerGamePlay(StickHeroGame stickHeroGame, StickHero stickHero, Stage stage, GameDetail game,String imageInput) {
       setStickHeroGame(stickHeroGame);
       setStickHero(stickHero);
       setStage(stage);
       this.game=game;
        StickHeroScene sc=game.getScenes().get(0);
       setSceneComponent(new StickHeroScene(sc.getA(),sc.getB(),sc.getDistance(),sc.getCherry()));
        setImageInput(imageInput);
        setCnt(-1);
    }

    @Override
    void nextScene() {
        getStickHeroGame().saveGameToLocal(game);
        if(getCnt()<game.getLength()-1) {
            StickHeroScene sc=game.getScenes().get(getCnt()+1);
            setSceneComponent(sc);
            playGame();

        }
        else{

            Random random=new Random();
            Block newB=new Block(random.nextInt(30,150));
            int newDistance=random.nextInt(55,200);


            int cherryPos= random.nextInt(15,newDistance-40);


            Cherry newCherry=new Cherry(cherryPos);
            game.addScene(getSceneComponent().getB(),newB,newDistance,newCherry);
            setSceneComponent(new StickHeroScene(getSceneComponent().getB(),newB,newDistance,newCherry));
            playGame();

        }
    }

    @Override
    Scene gameOver() {

        MusicService.getInstance().playMusic(MusicService.Musics.GameOver);
        getStickHeroGame().saveGameToUser(game.getId());
        getStickHero().addgame(game);
        DataBaseService.updateGame(game);
        Group root = new Group();
        ScrollPane scrollPane = new ScrollPane(root);

       getStickHeroGame().commitData();

        String gameId=game.getId();

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

            controllerB.receiveStickHero(getStickHero());

            getStage().setScene(new Scene(root2));
            getStage().show();
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
