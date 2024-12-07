package org.example;

import static java.lang.Math.abs;

public abstract class NPC implements Character {
    static private int LastID = 0;
    private final String NAME;
    private final int ID;
    private static int hpMax;
    private int HP;
    private int DEF;
    private int ATK;
    private int range;
    private int posX;
    private int posY;

    public NPC(int hpMax, String NAME, int DEF, int ATK, int range, int posX, int posY) {
        this.ID = ++LastID;
        this.hpMax = hpMax;
        this.NAME = NAME;
        this.HP = hpMax;
        this.DEF = DEF;
        this.ATK = ATK;
        this.range = range;
        this.posX = posX;
        this.posY = posY;
    }

    public int getHP() {
        return HP;
    }

    public String getNAME() {
        return NAME;
    }

    public int getDEF() {
        return DEF;
    }

    public int getATK() {
        return ATK;
    }

    public int getID() {
        return ID;
    }

    public int getRange() {
        return range;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void takeDamage(int damage) {
        this.HP -= damage;
    }

    public void attack(int damage, Character target) {
        int distance = abs((target.getPosX() + target.getPosY()) - (getPosX() + getPosY()));
        if (distance >= 0 && distance <= range) {
            target.takeDamage(damage);
        }
    }

    public void heal(int heal) {
        if (heal + this.HP > hpMax) {
            this.HP = hpMax;
        }
    }

    public void shield() {
        this.DEF += 5;
    }

    public boolean isAlive() {
        return HP > 0;
    }

    @Override
    public String toString() {
        return NAME + "{" +
                "ID=" + ID +
                ", hpMax=" + hpMax +
                ", HP=" + HP +
                ", DEF=" + DEF +
                ", ATK=" + ATK +
                ", range=" + range +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }

}
