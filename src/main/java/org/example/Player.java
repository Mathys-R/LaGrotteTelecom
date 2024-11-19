package org.example;

public class Player implements Character{
    private int HP;
    private int DEF;
    private int ATK;
    private String NAME;

    public Player(int HP, int DEF, int ATK, String NAME) {
        this.HP = HP;
        this.DEF = DEF;
        this.ATK = ATK;
        this.NAME = NAME;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public void takeDamage(int damage) {
        this.HP -= damage;
    }

    @Override
    public void attack(int damage, Character target) {
        target.takeDamage(damage);
    }

    @Override
    public void heal(int heal) {
        this.HP += heal;
    }

    @Override
    public void shield() {
        this.DEF += 5;
    }
}