package org.example;

public interface Character {
    void takeDamage(int damage);

    void attack(int damage, Character target);

    void heal(int heal);

    void shield();

    boolean isAlive();

    int getHP();

    int getDEF();

    int getATK();

    int getRange();

    int getPosX();

    int getPosY();

    String getName();

    void setPosX(int posX);

    void setPosY(int posY);
}
