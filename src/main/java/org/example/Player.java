package org.example;

import static java.lang.Math.abs;

public class Player implements Character {
    private final String NAME;
    private final int hpMax;
    private int HP;
    private int DEF;
    private int ATK;
    private int range;
    private int posX;
    private int posY;

    public Player(String NAME) {
        this.NAME = NAME;
        this.posX = 0;
        this.posY = 0;

        if (CheatMode.isCheatMode()) {
            this.hpMax = 10000; // Mode cheat : HP boostés
            this.HP = 10000;
            this.DEF = 10000;
            this.ATK = 10000;
            this.range = 10000;
        } else {
            this.hpMax = 100; // Valeurs par défaut
            this.HP = 100;
            this.DEF = 10;
            this.ATK = 15;
            this.range = 2;
        }
    }

    public String getNAME() {
        return NAME;
    }

    public int getHP() {
        return HP;
    }

    public int getDEF() {
        return DEF;
    }

    public int getATK() {
        return ATK;
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
        } else
            System.out.println("La cible est hors de portée !");
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