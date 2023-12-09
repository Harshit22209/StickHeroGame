package com.game.stick_hero.StickHeroComponents;

import java.util.ArrayList;

//Scenes:
//        -Block A
//        -Block B
//        -int dist
//        -background img
//        -cherries.
//        -lvl
public class StickHeroScene {
    private Block A;
    private Block B;
    private int distance;
    private String backgroundImg;
    private Cherry cherry;
    private int lvl;

    @Override
    public String toString() {
        return A.getWidth()+":"+B.getWidth()+":"+distance+":"+cherry.getPosition();
    }

    public StickHeroScene(Block a, Block b, int distance,Cherry cherry) {
        A = a;
        B = b;
        this.cherry=cherry;
        this.distance = distance;
    }

    public Block getA() {
        return A;
    }

    public void setA(Block a) {
        A = a;
    }

    public Block getB() {
        return B;
    }

    public void setB(Block b) {
        B = b;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public Cherry getCherry() {
        return cherry;
    }

    public void setCherry(Cherry cherry) {
        this.cherry = cherry;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
}
