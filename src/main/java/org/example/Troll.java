package org.example;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Classe représentant un Troll dans le jeu.
 * Hérite de la classe {@link NPC}.
 * Les trolls ont des caractéristiques spécifiques comme la santé, l'attaque, et la défense.
 * Ils possèdent également un nom généré de manière aléatoire parmi une liste prédéfinie.
 */
public class Troll extends NPC {

    private static final String[] NOMS_TROLL = { "Gourmant", "Paresseux", "Costaud", "Grogon" };

    // Logger pour cette classe
    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur pour créer un Troll à une position donnée.
     * Le nom du Troll est généré aléatoirement parmi une liste prédéfinie.
     *
     * @param posX La position X du Troll sur la carte.
     * @param posY La position Y du Troll sur la carte.
     */
    public Troll(int posX, int posY) {
        super(genererNomAleatoire(), 100, 20, 50, 1, posX, posY);

        // Log de la création du Troll
        logger.info("Un Troll a été créé avec le nom : " + this.getName() +
                ", à la position (" + posX + ", " + posY + ")");
    }

    /**
     * Génère un nom de Troll aléatoire à partir d'une liste prédéfinie.
     *
     * @return Un nom aléatoire pour un Troll.
     */
    private static String genererNomAleatoire() {
        Random r = new Random();
        String nom = "Troll " + NOMS_TROLL[r.nextInt(NOMS_TROLL.length)];
        return nom;
    }
}
