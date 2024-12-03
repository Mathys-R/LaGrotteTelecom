package org.example;

import java.util.Random;

public class Goblin extends NPC implements Character {

    // Liste des noms possibles pour les gobelins
    private static final String[] NOMS_GOBLIN = {"Filou", "Rusé", "Grincheux", "Minaud", "Perfide"};

    public Goblin(int posX, int posY) {
        // Appelle le constructeur parent avec un nom aléatoire et des statistiques spécifiques
        super(genererNomAleatoire(), 500, 10, 30, 1, posX, posY);
    }

    // Méthode statique pour générer un nom de Goblin aléatoire
    private static String genererNomAleatoire() {
        Random r = new Random();
        String nom = "Goblin " + NOMS_GOBLIN[r.nextInt(NOMS_GOBLIN.length)];
        return nom;
    }
}

