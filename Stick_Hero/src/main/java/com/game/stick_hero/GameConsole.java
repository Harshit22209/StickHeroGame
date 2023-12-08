package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.StickHeroScene;

// -platform/Game(Stick Hero Game)
//         -actionbutton1(Character)
//         -actionbutton2(Character)
//         -Sound-
//         -Save State:
public interface GameConsole {
     //void move();
     void up();
     void down();
     void playBackgroundSound();
     void saveState();
     StickHeroScene loadState();
     void pause();
     void resume();

}
