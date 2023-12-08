package com.game.stick_hero.StickHeroComponents;

import com.game.stick_hero.Character;

import java.util.ArrayList;

// -getStick()->Stick create
//         -name
//         -noOfcherries
//         -isFlipped
//         -maxScore
//         -Games History
//         -Revive()
//         -HaveCherries
public class StickHero extends Character {
    Stick stick;
    private boolean isFlipped;
    private int noOfCherries;

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    private int maxScore;
    private int currScore;
    private ArrayList<GameDetail> gamesPlayed;
    public StickHero(String name, String id,int bestScore,ArrayList<GameDetail> gamesPlayed){
        super(name,id);
        maxScore=bestScore;
        this.gamesPlayed=gamesPlayed;
    }
    public ArrayList<GameDetail> getGamesPlayed(){
        return gamesPlayed;
    }
    public void addgame(GameDetail game){
        gamesPlayed.add(game);
    }
    public void flipUpward() {
    }

    public void flipDown() {
    }

    public void createStick() {
        stick=new Stick();
    }
    public  void increaseLength() {
        stick.incLength();
    }
    public static void revive(){

    }
}
