package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.Block;
import com.game.stick_hero.StickHeroComponents.Cherry;
import com.game.stick_hero.StickHeroComponents.StickHero;
import com.game.stick_hero.StickHeroComponents.StickHeroScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class SoloGamePlay extends GamePlay{

    public SoloGamePlay(StickHero stickHero, StickHeroGame stickHeroGame, Stage stage, String imageInput) {
        setStickHero(stickHero);
        setStickHeroGame(stickHeroGame);
        Random random=new Random();
        int newD=random.nextInt(55, 200);
        setStage(stage);
        setCnt(-1);
        setImageInput(imageInput);
        setSceneComponent(new StickHeroScene(new Block(random.nextInt(30, 150)),
                                          new Block(random.nextInt(30, 150)),
                                          newD,
                                          new Cherry(random.nextInt(15, newD-40))
                                            ));

    }

    @Override
    void nextScene() {
        Random random=new Random();
        Block newB = new Block(random.nextInt(30, 150));
        int newD = random.nextInt(60, 200);
        Cherry ch=new Cherry(random.nextInt(15, newD-40));
        setSceneComponent(new StickHeroScene(getSceneComponent().getB(),newB,newD,ch));
        playGame();
    }

    @Override
    Scene gameOver() {
        MusicService.getInstance().playMusic(MusicService.Musics.GameOver);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        GameController controllerB = loader.getController();
        controllerB.setScore(getCnt());
        Scene scene = new Scene(root);
        return scene;
    }
}
