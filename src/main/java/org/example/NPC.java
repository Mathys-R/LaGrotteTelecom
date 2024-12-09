package org.example;

import static java.lang.Math.abs;
import java.util.logging.Logger;

/**
 * La classe abstraite {@code NPC} représente un personnage non-joueur (PNJ) dans le jeu.
 * Elle implémente l'interface {@code Character} et définit les caractéristiques de base
 * des PNJs tels que les points de vie, la défense, l'attaque, la portée d'attaque, et les
 * coordonnées sur la carte. Chaque instance de {@code NPC} possède un identifiant unique.
 *
 * Un logger est utilisé pour enregistrer les événements liés aux actions du PNJ (dommages, attaques, guérisons).
 */
public abstract class NPC implements Character {

    /** Identifiant unique pour chaque instance de {@code NPC}. */
    static private int LastID = 0;
    /** Identifiant du PNJ. */
    private final int ID;
    /** Nom du PNJ. */
    private String name;
    /** Points de vie maximum du PNJ. */
    private int HP_MAX;
    /** Points de vie actuels du PNJ. */
    private int hp;
    /** Défense du PNJ. */
    private int DEF;
    /** Attaque du PNJ. */
    private int ATK;
    /** Portée d'attaque du PNJ. */
    private int range;
    /** Position X du PNJ sur la carte. */
    private int posX;
    /** Position Y du PNJ sur la carte. */
    private int posY;
    /** Logger utilisé pour enregistrer les actions du PNJ. */
    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur pour créer un PNJ avec des caractéristiques spécifiques.
     *
     * @param name Le nom du PNJ.
     * @param HP_MAX Les points de vie maximum du PNJ.
     * @param DEF La défense du PNJ.
     * @param ATK L'attaque du PNJ.
     * @param range La portée d'attaque du PNJ.
     * @param posX La position X du PNJ.
     * @param posY La position Y du PNJ.
     */
    public NPC(String name, int HP_MAX, int DEF, int ATK, int range, int posX, int posY) {
        this.ID = ++LastID;
        this.name = name;
        this.HP_MAX = HP_MAX;
        this.hp = HP_MAX;
        this.DEF = DEF;
        this.ATK = ATK;
        this.range = range;
        this.posX = posX;
        this.posY = posY;

        logger.info("Création d'un nouveau PNJ: " + this);
    }

    // Getters et setters pour les attributs
    public String getName() {
        return name;
    }
    public int getHp() {
        return hp;
    }
    public int getHP_MAX() {
        return HP_MAX;
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

    public void setName(String name) {
        this.name = name;
    }
    public void setHp(int hp) {
        this.hp = hp;
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

    /**
     * Applique les dégâts au PNJ. Les dégâts sont réduits en fonction de la défense du PNJ.
     *
     * @param damage Les dégâts à infliger.
     */
    public void takeDamage(int damage) {
        double reduction = 1 - (this.getDEF() / (this.getDEF() + 50.0));
        int damageTaken = (int)(damage * reduction);
        this.hp -= damageTaken;
        logger.info(this.name + " a pris " + damageTaken + " dégâts, HP restants: " + this.hp);
    }
    /**
     * Effectue une attaque sur une cible si elle est à portée.
     * La distance est calculée en fonction de la position de la cible et du PNJ.
     *
     * @param damage Les dégâts de l'attaque.
     * @param target La cible à attaquer.
     */
    public void attack(int damage, Character target) {
        int distance = abs((target.getPosX() + target.getPosY()) - (getPosX() + getPosY()));
        if (distance >= 0 && distance <= range) {
            target.takeDamage(damage);
            logger.info(this.name + " attaque " + target.getName() + " pour " + damage + " dégâts.");
        } else {
            logger.warning(this.name + " est hors de portée pour attaquer " + target.getName() + ".");
        }
    }
    /**
     * Guérit le PNJ d'un certain montant de points de vie, jusqu'à la limite de ses points de vie maximum.
     *
     * @param heal Le montant de points de vie à restaurer.
     */
    public void heal(int heal) {
        if (heal > 0) {
            this.hp = Math.min(this.hp + heal, HP_MAX);
            logger.info(this.name + " a été soigné de " + heal + " points de vie.");
        } else {
            logger.warning(this.name + " a tenté de se soigner avec une valeur négative ou nulle.");
        }
    }
    /**
     * Augmente la défense du PNJ de 5 points en activant un bouclier.
     */
    public void shield() {
        this.DEF += 5;
        logger.info(this.name + " active un bouclier, défense augmentée à " + this.DEF);
    }
    /**
     * Vérifie si le PNJ est encore en vie.
     *
     * @return {@code true} si le PNJ est en vie, {@code false} sinon.
     */
    public boolean isAlive() {
        return hp > 0;
    }
    /**
     * Met le PNJ dans un état "mort" ou "vivant" en ajustant ses points de vie.
     *
     * @param b Si {@code true}, le PNJ est mis dans un état "vivant" avec 1 point de vie.
     *          Si {@code false}, le PNJ est mis dans un état "mort" avec 0 point de vie.
     */
    public void setAlive(boolean b) {
        if (b == true) {
            this.hp = 1;
            logger.info(this.name + " est réanimé et a maintenant " + this.hp + " point de vie.");
        } else {
            this.hp = 0;
            logger.info(this.name + " est mort.");
        }
    }

    /**
     * Méthode pour obtenir une représentation sous forme de chaîne de caractères de ce PNJ.
     *
     * @return Une chaîne représentant le PNJ.
     */
    @Override
    public String toString() {
        return name + "{" +
                "ID=" + ID +
                ", HP_MAX=" + HP_MAX +
                ", hp=" + hp +
                ", DEF=" + DEF +
                ", ATK=" + ATK +
                ", range=" + range +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
