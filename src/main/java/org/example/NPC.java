package org.example;

public abstract class NPC implements Character {
    static private int LastID = 0;
    private int ID;
    private int HP;
    private int DEF;
    private int ATK;

    public NPC(int HP, int DEF, int ATK) {
        this.ID = ++LastID;
        this.HP = HP;
        this.DEF = DEF;
        this.ATK = ATK;
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
    public int getID() {
        return ID;
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
