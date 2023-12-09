package com.game.stick_hero.StickHeroComponents;

import java.io.Serializable;
import java.util.ArrayList;

public class GameDetail implements Serializable {
    public int getScore() {
        return score;
    }
    public void setScore(int score){
        this.score=score;
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<StickHeroScene> getScenes() {
        return scenes;
    }

    private int score;
    private int bestScore;
    private String userId;
    private int CherryCollected;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCherryCollected() {
        return CherryCollected;
    }

    public void setCherryCollected(int cherryCollected) {
        CherryCollected = cherryCollected;
    }

   transient  private ArrayList<StickHeroScene> scenes;
    public GameDetail(int score, ArrayList<StickHeroScene> scenes,int bestScore,String id){
        this.score=score;
        this.scenes=scenes;
        this.bestScore=bestScore;
        this.id=id;

    }
    public void addScene(Block A,Block B,int d,Cherry cherry){
        scenes.add(new StickHeroScene(A,B,d,cherry));
    }
    public int getbestScore() {
        return bestScore;
    }

    public int getLength() {
        return scenes.size();
    }
}
