package org.example;

import java.util.Random;

public class Troll extends NPC implements Character{


    private static final String[] NOMS_TROLL = {"Gourmant", "Paresseux", "Costaud", "Grogon"};

    public Troll(int posX, int posY) {

        super(genererNomAleatoire(), 100, 20, 50, 1,posX, posY);
    }
    // Méthode statique pour générer un nom de Troll aléatoire
    private static String genererNomAleatoire() {
        Random r = new Random();
        String nom = "Troll " + NOMS_TROLL[r.nextInt(NOMS_TROLL.length)];
        return nom;
    }
}
