package org.example;

import static java.lang.Math.abs;
import java.util.logging.Logger;

/**
 * La classe {@code Player} représente un personnage contrôlé par le joueur.
 * Elle implémente l'interface {@code Character} et possède des caractéristiques telles que
 * les points de vie, la défense, l'attaque, la portée d'attaque, et la position sur la carte.
 * Un mode "cheat" permet de booster ces caractéristiques pour le joueur.
 *
 * Un logger est utilisé pour enregistrer les événements majeurs comme les attaques, les dégâts subis,
 * les soins et les changements d'état du joueur.
 */
public class Player implements Character {

    /** Nom du joueur. */
    private final String name;

    /** Points de vie maximum du joueur. */
    private final int HP_MAX;

    /** Points de vie actuels du joueur. */
    private int hp;

    /** Défense du joueur. */
    private int DEF;

    /** Attaque du joueur. */
    private int ATK;

    /** Portée d'attaque du joueur. */
    private int range;

    /** Position X du joueur sur la carte. */
    private int posX;

    /** Position Y du joueur sur la carte. */
    private int posY;

    /** Logger utilisé pour enregistrer les actions du joueur. */
    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur pour créer un joueur avec des caractéristiques basées sur le mode cheat.
     * Si le mode cheat est activé, les points de vie, la défense, l'attaque et la portée sont
     * considérablement augmentés. Sinon, le joueur a des valeurs par défaut.
     *
     * @param name Le nom du joueur.
     */
    public Player(String name) {
        this.name = name;
        this.posX = 0;
        this.posY = 1;

        if (CheatMode.isCheatMode()) {
            // Mode cheat : caractéristiques boostées
            this.HP_MAX = 10000;
            this.hp = 10000;
            this.DEF = 10000;
            this.ATK = 10000;
            this.range = 10000;
            logger.info(name + " a activé le mode cheat avec des caractéristiques boostées.");
        } else {
            // Valeurs par défaut
            this.HP_MAX = 300;
            this.hp = 300;
            this.DEF = 20;
            this.ATK = 50;
            this.range = 2;
            logger.info(name + " a été créé avec les caractéristiques par défaut.");
        }
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

    public int getRange() {
        return range;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setHp(int HP) {
        this.hp = HP;
        logger.info(name + " a ses points de vie mis à jour : " + this.hp);
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
        logger.info(name + " a sa défense mise à jour : " + this.DEF);
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
        logger.info(name + " a son attaque mise à jour : " + this.ATK);
    }

    public void setRange(int range) {
        this.range = range;
        logger.info(name + " a sa portée mise à jour : " + this.range);
    }

    public void setPosX(int posX) {
        this.posX = posX;
        logger.info(name + " a sa position X mise à jour : " + this.posX);
    }

    public void setPosY(int posY) {
        this.posY = posY;
        logger.info(name + " a sa position Y mise à jour : " + this.posY);
    }

    /**
     * Applique les dégâts au joueur, réduits en fonction de la défense.
     *
     * @param damage Les dégâts à infliger au joueur.
     */
    public void takeDamage(int damage) {
        int damageTaken = (int) (damage * (1 - (this.getDEF() / (this.getDEF() + 50.0))));
        if (this.hp-damageTaken<0){
            setHp(0);
        }else {
            setHp(this.hp-damageTaken);
        }
        logger.info(this.name + " a pris " + damageTaken + " dégâts, HP restants: " + this.hp);
    }

    /**
     * Effectue une attaque sur une cible si elle est dans la portée du joueur.
     * La distance est calculée en fonction des positions X et Y.
     *
     * @param damage Les dégâts de l'attaque.
     * @param target La cible de l'attaque.
     */
    public void attack(int damage, Character target) {
        int distance = abs((target.getPosX() + target.getPosY()) - (getPosX() + getPosY()));
        if (distance >= 0 && distance <= range) {
            target.takeDamage(damage);
            logger.info(name + " attaque " + target.getName() + " pour " + damage + " dégâts.");
        } else {
            logger.warning(name + " a tenté d'attaquer " + target.getName() + " mais la cible est hors de portée.");
            System.out.println("La cible est hors de portée !");
        }
    }

    /**
     * Soigne le joueur en lui donnant des points de vie supplémentaires,
     * sans dépasser ses points de vie maximum.
     *
     * @param heal Le montant de points de vie à restaurer.
     */
    public void heal(int heal) {
        if (heal > 0) {
            this.hp = Math.min(this.hp + heal, HP_MAX);
            logger.info(name + " a été soigné de " + heal + " points de vie. HP actuels : " + this.hp);
        } else {
            logger.warning(name + " a tenté de se soigner avec une valeur négative ou nulle.");
        }
    }

    /**
     * Augmente la défense du joueur de 5 points en activant un bouclier.
     */
    public void shield() {
        this.DEF += 5;
        logger.info(name + " a activé un bouclier, sa défense est maintenant de " + this.DEF);
    }

    /**
     * Vérifie si le joueur est toujours en vie.
     *
     * @return {@code true} si le joueur est en vie, {@code false} sinon.
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du joueur.
     *
     * @return Une chaîne représentant le joueur.
     */
    @Override
    public String toString() {
        return name + "{" +
                ", hpMax=" + HP_MAX +
                ", HP=" + hp +
                ", DEF=" + DEF +
                ", ATK=" + ATK +
                ", range=" + range +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
