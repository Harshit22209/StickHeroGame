package com.game.stick_hero;
import javafx.scene.media.*;
import javafx.util.Duration;


// used two Design Patterns here:
// 1.Singleton
// 2.Facade
import java.io.File;

public class MusicService {
    static enum Musics{
        Bonus,StartBackGround,PauseBackGround,GameOver;
    }
    MediaPlayer[] mediaPlayers;
    static MusicService musicService;
    private MusicService(){
        mediaPlayers=new MediaPlayer[4];

        mediaPlayers[0] = new MediaPlayer(new Media(new File(".\\src\\main\\resources\\com\\game\\stick_hero\\mixkit-arcade-bonus-229.mp3").toURI().toString()));
        mediaPlayers[1] = new MediaPlayer(new Media(new File(".\\src\\main\\resources\\com\\game\\stick_hero\\mixkit-arcade-retro-background-219.mp3").toURI().toString()));
        mediaPlayers[2] = new MediaPlayer(new Media(new File(".\\src\\main\\resources\\com\\game\\stick_hero\\game-over-arcade-6435.mp3").toURI().toString()));
        mediaPlayers[3] = new MediaPlayer(new Media(new File(".\\src\\main\\resources\\com\\game\\stick_hero\\8bit-music-for-game-68698.mp3").toURI().toString()));


    }
    public static MusicService getInstance(){
        if(musicService==null){
            musicService=new MusicService();
        }
        return musicService;
    }
    public void playMusic(Musics music){
        switch (music){
            case Bonus :
                mediaPlayers[0].setCycleCount(1);
                mediaPlayers[0].seek(Duration.ZERO);
                mediaPlayers[0].play();
            case GameOver:
                mediaPlayers[2].setCycleCount(1);
                mediaPlayers[2].seek(Duration.ZERO);
                mediaPlayers[2].play();
//

               // System.out.println(mediaPlayers[2].getStatus()==MediaPlayer.Status.PLAYING);

        }
    }
}
