package com.example.app_test.Creature;

public class Creature{
    private String name;
    private int lvl;
    private int hp;
    private int atk;
    private int def;

    public Creature(){}

    public Creature(String name){
        this.name = name;
        lvl = hp = atk = def = 1;
    }

    public String getName(){
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getLvl(){
        return lvl;
    }

    public void setLvl(int lvl)
    {
        this.lvl = lvl;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getAtk(){
        return atk;
    }

    public void setAtk(int atk){
        this.atk = atk;
    }

    public int getDef()
    {
        return def;
    }

    public void setDef(int def){
        this.def = def;
    }
}