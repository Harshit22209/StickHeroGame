package com.game.stick_hero;


import com.game.stick_hero.StickHeroComponents.StickHero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;


public class GameController {


    private static StickHeroGame stickHeroGame;
    private static StickHero stickHero;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label finalScore;
    @FXML
    private Label bestScore;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label totalCherries;
    @FXML
    private Label cherriesRequired;
    @FXML
    private Label errorRevive;
    private static int bestSc = 0;
    private int cnt = 0;
    private static SoloGamePlay solo;

    @FXML
    public void revive(ActionEvent e) throws URISyntaxException {

        int required= Integer.parseInt(cherriesRequired.getText());
        if(stickHero.getNoOfCherries()<required){
            errorRevive.setText("Need More Cherries");
            return;
        }

        stickHeroGame.setCherry(0);
        stickHeroGame.revive(required);
        solo.nextScene();




    }
    public void receiveStickHero(StickHero Hero) {

        System.out.println(Hero.getName());

        stickHero = Hero;
    }
    static String imageInput="";
    @FXML
    public void playGame(ActionEvent e) throws URISyntaxException {

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Random random = new Random();

        imageInput="bg"+random.nextInt(1,4)+".jpeg";

        stickHeroGame = new StickHeroGame(stickHero);
        stickHeroGame.startGame();
        solo=new SoloGamePlay(stickHero,stickHeroGame,stage,imageInput);
        solo.playGame();

    }

    @FXML
    public void goToMenu(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(MultiplayerController.class.getResource("GameMenu.fxml"));
        Parent root2 = null;
        try {
            root2 = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        GameController controllerB = loader.getController();


        controllerB.receiveStickHero(stickHero);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root2));
        stage.show();
    }

    public void setScore(int cnt) {
        finalScore.setText(cnt + "");
        int best = stickHero.getMaxScore();
        stickHero.addCherries(stickHeroGame.getCherryCount());
        DataBaseService.updateCherriesOfUser(stickHero.getId(),stickHero.getNoOfCherries());
        if (cnt > best) {

            stickHero.setMaxScore(cnt);
            DataBaseService.updateScoreOfUser(stickHero.getId(), cnt);

        }
        bestScore.setText(stickHero.getMaxScore() + "");
        cherriesRequired.setText(((stickHeroGame.getNoOFRevives()+1)*5)+"");
        totalCherries.setText(stickHero.getNoOfCherries()+"");
    }


    @FXML
    public void showProfile(ActionEvent e) {

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Image image = new Image(getClass().getResourceAsStream("profile.png"));
        ProfileController.showMyProfile(stage, stickHero, image);

    }

    @FXML
    public void compete(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        MultiplayerController.showOtherPlayers(stage, stickHero);
    }

    @FXML
    private Label saveText;

    @FXML
    public void saveGame() {
        if (!stickHeroGame.isSaved()) {
            stickHeroGame.saveGame();

            saveText.setText("Saved Successfuly");
        } else {
            saveText.setText("Already Saved");
            saveText.setTextFill(Color.ALICEBLUE);
        }
    }



}