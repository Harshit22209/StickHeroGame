package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

//StickHeroGame:
//        -extendLength(StickHero)
//        -flip(Stick Hero)
//        -Scenes
//        -diff diff backgrounds Array
//        -Users:
//        name ,id
//SingleTon-for sure
public class StickHeroGame implements GameConsole {
    private  StickHero stickHero;
    private static ArrayList<String> backgroundImgs;

    private static ArrayList<StickHeroScene> scenes;
    private static ArrayList<Character> players;
    int score;
    private boolean saveStatus=false;
    int cherryCount;
    private int noOfRevives=0;

    public int getNoOFRevives() {
        return noOfRevives;
    }

    public void incNoOFRevives() {
        noOfRevives++;
    }

    void setScore(int cnt){
        score=cnt;
    }
    StickHeroGame(String name,String id){
        stickHero=new StickHero(name, id,0,new ArrayList<>(),0);
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
        MusicService.getInstance().playMusic(MusicService.Musics.StartBackGround);

    }
    public void startGame(){
        scenes=new ArrayList<>();
        cherryCount=0;
        noOfRevives=0;
    }
    public void addScene(Block A, Block B, int d, Cherry cherry){
        scenes.add(new StickHeroScene(A,B,d,cherry));
    }
    public void saveGame(){
        String gameId=DataBaseService.createId();

        DataBaseService.addGame(gameId,scenes,score);
        stickHero.addgame(new GameDetail(score,scenes,score,gameId));
      saveGameToUser(gameId);
        saveStatus=true;
    }
    public boolean isSaved(){
        return saveStatus;
    }
    public void saveGameToUser(String GameID){

        DataBaseService.addGameToUser(getId(),GameID,score);
    }

    public void saveGameToLocal(GameDetail game){
        game.setUserId(getId());
        game.setCherryCollected(cherryCount);
        try (ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("./Local/"+getId()+".ser"))) {
            // Serialize and write the objects
            out.writeObject(game);
            System.out.println("Objects serialized successfully.");

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void commitData() {
        try {
            if(score==0) return;
            Files.delete(Paths.get("./Local/"+getId()+".ser"));
            System.out.println("Deleted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveState() {

    }

    @Override
    public StickHeroScene loadState() {
        return scenes.get(scenes.size()-1);
    }

    @Override
    public void pause() {}
    @Override
    public void resume(){}

    public boolean hasCherry() {
        return scenes.get(scenes.size()-1).getCherry().getPosition()>0;
    }

    public void incCherry() {
        cherryCount++;
    }
    public int getCherryCount(){
        return cherryCount;
    }
    public Block revive(int cherriesRequired){
        stickHero.revive(cherriesRequired);
        noOfRevives++;
        DataBaseService.updateCherriesOfUser(getId(), stickHero.getNoOfCherries());
        return scenes.get(scenes.size()-1).getB();
    }

    public void setCherry(int i) {
        cherryCount=i;
    }
}
