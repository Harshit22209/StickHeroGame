package com.game.stick_hero.StickHeroComponents;

import com.game.stick_hero.Character;
import com.game.stick_hero.DataBaseService;

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

    public int getNoOfCherries() {
        return noOfCherries;
    }

    public void setNoOfCherries(int noOfCherries) {
        this.noOfCherries = noOfCherries;
    }

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
    public StickHero(String name, String id,int bestScore,ArrayList<GameDetail> gamesPlayed,int noOfCherries){
        super(name,id);
        maxScore=bestScore;
        this.gamesPlayed=gamesPlayed;
        this.noOfCherries=noOfCherries;
    }
    public ArrayList<GameDetail> getGamesPlayed(){
        return gamesPlayed;
    }
    public void addgame(GameDetail game){
        gamesPlayed.add(game);
    }


    public void createStick() {
        stick=new Stick();
    }
    public  void increaseLength() {
        stick.incLength();
    }


    public boolean getIsFlipped() {
        return isFlipped;
    }
    public void toggleFlip(){
        isFlipped=!isFlipped;

    }
    public void setFlipped(boolean value){
        isFlipped=value;
    }

    public void flipDown() {
    }

    public void flipUpward() {
    }

    public void addCherries(int cherryCount) {
        noOfCherries+=cherryCount;

    }
    public void revive(int c){
        System.out.println(c+" "+noOfCherries);

        noOfCherries-=c;
    }

    public boolean hasPlayed(String gameId) {
        System.out.println(getId()+" "+gameId);
        for(var game:gamesPlayed){
            System.out.println(game.getId());
            if(game.getId().equals(gameId)) return true;
        }
        return false;
    }
}
