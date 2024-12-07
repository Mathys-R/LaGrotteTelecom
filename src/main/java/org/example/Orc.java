package org.example;

import java.util.Random;

public class Orc extends NPC implements Character {

    // Liste des noms possibles pour les orques
    private static final String[] NOMS_ORC = {"Brutal", "Sanguinaire", "Féroce", "Implacable", "Terrifiant"};

    public Orc(int posX, int posY) {
        // Appelle le constructeur parent avec un nom aléatoire et des statistiques spécifiques
        super(genererNomAleatoire(), 1200, 40, 60, 2, posX, posY);
    }

    // Méthode statique pour générer un nom d'Orc aléatoire
    private static String genererNomAleatoire() {
        Random r = new Random();
        String nom = "Orc " + NOMS_ORC[r.nextInt(NOMS_ORC.length)];
        return nom;
    }
}
