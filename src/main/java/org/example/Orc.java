package org.example;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Classe représentant un Orc dans le jeu.
 * Hérite de la classe {@link NPC}.
 * Les orcs ont des caractéristiques spécifiques comme la santé, l'attaque, et la défense.
 * Ils possèdent également un nom généré de manière aléatoire parmi une liste prédéfinie.
 */
public class Orc extends NPC {

    // Liste des noms possibles pour les orques
    private static final String[] NOMS_ORC = { "Brutal", "Sanguinaire", "Féroce", "Implacable", "Terrifiant" };

    // Logger pour cette classe
    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur pour créer un Orc à une position donnée.
     * Le nom de l'Orc est généré aléatoirement parmi une liste prédéfinie.
     *
     * @param posX La position X de l'Orc sur la carte.
     * @param posY La position Y de l'Orc sur la carte.
     */
    public Orc(int posX, int posY) {
        // Appelle le constructeur parent avec un nom aléatoire et des statistiques
        super(genererNomAleatoire(), 200, 40, 60, 2, posX, posY);

        // Log de la création de l'Orc
        logger.info("Un Orc a été créé avec le nom : " + this.getName() +
                ", à la position (" + posX + ", " + posY + ")");
    }

    /**
     * Génère un nom d'Orc aléatoire à partir d'une liste prédéfinie.
     *
     * @return Un nom aléatoire pour un Orc.
     */
    private static String genererNomAleatoire() {
        Random r = new Random();
        String nom = "Orc " + NOMS_ORC[r.nextInt(NOMS_ORC.length)];
        return nom;
    }
}
