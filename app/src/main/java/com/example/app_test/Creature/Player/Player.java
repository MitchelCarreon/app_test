package com.example.app_test.Creature.Player;

public class Player extends com.example.app_test.Creature.Creature {
    private String name;
    private int lvl;
    private int hp;
    private int atk;
    private int def;

    public Player(String name){
            this.name = name;
            this.lvl = 1;
            this.hp = 10;
            this.atk = 5;
            this.def = 2;
    }
}
