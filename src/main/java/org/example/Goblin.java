package org.example;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Classe représentant un Goblin dans le jeu.
 * Hérite de la classe {@link NPC}.
 * Les gobelins ont des caractéristiques spécifiques comme la santé, l'attaque, et la défense.
 * Ils possèdent également un nom généré de manière aléatoire parmi une liste prédéfinie.
 */
public class Goblin extends NPC {

    // Liste des noms possibles pour les gobelins
    private static final String[] NOMS_GOBLIN = { "Filou", "Rusé", "Grincheux", "Minaud", "Perfide" };

    // Logger pour cette classe
    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur pour créer un Goblin à une position donnée.
     * Le nom du Goblin est généré aléatoirement parmi une liste prédéfinie.
     *
     * @param posX La position X du Goblin sur la carte.
     * @param posY La position Y du Goblin sur la carte.
     */
    public Goblin(int posX, int posY) {
        // Appelle le constructeur parent avec un nom aléatoire et des statistiques
        super(genererNomAleatoire(), 50, 10, 30, 1, posX, posY);

        // Log de la création du Goblin
        logger.info("Un Goblin a été créé avec le nom : " + this.getName() +
                ", à la position (" + posX + ", " + posY + ")");
    }

    /**
     * Génère un nom de Goblin aléatoire à partir d'une liste prédéfinie.
     *
     * @return Un nom aléatoire pour un Goblin.
     */
    private static String genererNomAleatoire() {
        Random r = new Random();
        String nom = "Goblin " + NOMS_GOBLIN[r.nextInt(NOMS_GOBLIN.length)];
        return nom;
    }
}
