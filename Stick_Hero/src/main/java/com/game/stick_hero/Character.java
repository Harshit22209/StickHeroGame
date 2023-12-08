package com.game.stick_hero;

public class Character {
    private String name;
    private String id;

    public Character(String name, String id) {
        this.name = name;
        this.id = id;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
