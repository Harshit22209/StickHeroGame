package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.Block;
import com.game.stick_hero.StickHeroComponents.GameDetail;
import com.game.stick_hero.StickHeroComponents.StickHero;
import com.game.stick_hero.StickHeroComponents.StickHeroScene;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;

//StickHeroGame:
//        -extendLength(StickHero)
//        -flip(Stick Hero)
//        -Scenes
//        -diff diff backgrounds Array
//        -Users:
//        name ,id
public class StickHeroGame implements GameConsole {
    private static StickHero stickHero;
    private static ArrayList<String> backgroundImgs;

    private static ArrayList<StickHeroScene> scenes;
    private static ArrayList<Character> players;
    int score;
    private boolean saveStatus=false;
    void setScore(int cnt){
        score=cnt;
    }
    StickHeroGame(String name,String id){
        stickHero=new StickHero(name, id,0,new ArrayList<>());
    }
    StickHeroGame(StickHero Hero){
        stickHero=Hero;
    }

    public String getName(){
        return stickHero.getName();
    }
    public String getId(){
        return stickHero.getId();
    }
   public void initiateStick(){
        stickHero.createStick();
   }

    public void increaseLength() {

        stickHero.increaseLength();

    }

    @Override

    public void up() {
        stickHero.flipUpward();

    }

    @Override
    public void down() {
        stickHero.flipDown();
    }

    @Override
    public void playBackgroundSound() {

    }
    public void startGame(){
        scenes=new ArrayList<>();
    }
    public void addScene(Block A, Block B, int d){
        scenes.add(new StickHeroScene(A,B,d));
    }
    public void saveGame(){
        String gameId=DataBaseService.createId();

        DataBaseService.addGame(gameId,scenes);
        stickHero.addgame(new GameDetail(score,scenes));
        saveGameToUser(gameId);
        saveStatus=true;
    }
    public boolean isSaved(){
        return saveStatus;
    }
    public void saveGameToUser(String GameID){
        DataBaseService.addGameToUser(getId(),GameID,score);
    }
    @Override
    public void saveState() {

    }

    @Override
    public StickHeroScene loadState() {
        return scenes.get(scenes.size()-1);
    }

    @Override
    public void pause() {

    }
    @Override
    public void resume(){

    }
}
