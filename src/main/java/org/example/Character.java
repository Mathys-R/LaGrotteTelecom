package org.example;

public interface Character {
    void takeDamage(int damage);
    void attack(int damage,Character target);
    void heal(int heal);
    void shield();

}
