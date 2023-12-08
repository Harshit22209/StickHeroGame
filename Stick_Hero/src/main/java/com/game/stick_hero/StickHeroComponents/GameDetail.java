package com.game.stick_hero.StickHeroComponents;

import java.util.ArrayList;

public class GameDetail {
    public int getScore() {
        return score;
    }
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<StickHeroScene> getScenes() {
        return scenes;
    }

    private int score;
    private ArrayList<StickHeroScene> scenes;
    public GameDetail(int score, ArrayList<StickHeroScene> scenes){
        this.score=score;
        this.scenes=scenes;
    }

}
