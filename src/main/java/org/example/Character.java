package org.example;

/**
 * Interface représentant un personnage dans le jeu.
 * Cette interface définit les méthodes de base pour gérer les caractéristiques et les actions
 * d'un personnage, comme prendre des dégâts, attaquer, se soigner, se défendre, etc.
 *
 * Les classes qui implémentent cette interface doivent fournir des comportements concrets et personnalisés pour
 * ces actions.
 */
public interface Character {

    /**
     * Applique des dégâts au personnage. La quantité de dégâts est réduite en fonction de la défense
     * du personnage, si applicable.
     *
     * @param damage La quantité de dégâts à infliger.
     */
    void takeDamage(int damage);

    /**
     * Attaque un autre personnage avec une certaine quantité de dégâts.
     *
     * @param damage La quantité de dégâts à infliger à la cible.
     * @param target Le personnage cible de l'attaque.
     */
    void attack(int damage, Character target);

    /**
     * Soigne le personnage d'une certaine quantité de points de vie.
     *
     * @param heal La quantité de points de vie à restaurer.
     */
    void heal(int heal);

    /**
     * Active le bouclier du personnage, qui permet de réduire les dégâts subis pendant un certain temps.
     * Cette méthode doit être implémentée pour gérer les effets du bouclier (si applicable).
     */
    void shield();

    /**
     * Vérifie si le personnage est toujours en vie, c'est-à-dire s'il a encore des points de vie.
     *
     * @return {@code true} si le personnage est en vie (points de vie > 0), {@code false} sinon.
     */
    boolean isAlive();

    /**
     * Récupère les points de vie actuels du personnage.
     *
     * @return Le nombre actuel de points de vie du personnage.
     */
    int getHp();

    /**
     * Récupère le nombre maximum de points de vie du personnage.
     *
     * @return Le nombre maximum de points de vie du personnage.
     */
    int getHP_MAX();

    /**
     * Récupère la défense du personnage.
     *
     * @return La valeur de la défense du personnage, qui réduit les dégâts subis.
     */
    int getDEF();

    /**
     * Récupère l'attaque du personnage.
     *
     * @return La valeur de l'attaque du personnage, qui détermine les dégâts infligés lors d'une attaque.
     */
    int getATK();

    /**
     * Récupère la portée d'attaque du personnage, c'est-à-dire la distance à laquelle il peut attaquer un ennemi.
     *
     * @return La portée d'attaque du personnage.
     */
    int getRange();

    /**
     * Récupère la position X du personnage sur la carte.
     *
     * @return La position X du personnage.
     */
    int getPosX();

    /**
     * Récupère la position Y du personnage sur la carte.
     *
     * @return La position Y du personnage.
     */
    int getPosY();

    /**
     * Récupère le nom du personnage.
     *
     * @return Le nom du personnage.
     */
    String getName();

    /**
     * Modifie la position X du personnage sur la carte.
     *
     * @param posX La nouvelle position X du personnage.
     */
    void setPosX(int posX);

    /**
     * Modifie la position Y du personnage sur la carte.
     *
     * @param posY La nouvelle position Y du personnage.
     */
    void setPosY(int posY);
}
