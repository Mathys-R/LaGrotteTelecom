package org.example;

import java.util.ArrayList;
import java.util.Random;

class Salle {
    private String nom;
    private int ameliorationAttaque;
    private int ameliorationDefense;
    private ArrayList<String>[] grille; // Tableau de lignes (ArrayList<String>)

    @SuppressWarnings("unchecked")
    public Salle(String nom, int ameliorationAttaque, int ameliorationDefense, Player player) {
        this.nom = nom;
        this.ameliorationAttaque = ameliorationAttaque;
        this.ameliorationDefense = ameliorationDefense;
        grille = creerMatrice(player); // Génération de la grille
    }

    public String getNom() {
        return nom;
    }

    // Permettrai de donner des boost à certains main character
    public int getAmeliorationAttaque() {
        return ameliorationAttaque;
    }
    public int getAmeliorationDefense() {
        return ameliorationDefense;
    }

    @Override
    public String toString() {
        return nom +
                " | Amélioration Attaque: " + ameliorationAttaque +
                " | Amélioration Défense: " + ameliorationDefense;
    }

    // Génère une matrice sous forme d'ArrayList<String>[]
    @SuppressWarnings("unchecked")
    private static ArrayList<String>[] creerMatrice(Player player) {
        Random r = new Random();

        // Dimensions de la grille
        int rows = 3;
        int cols = 6 + r.nextInt(3);

        ArrayList<String>[] grille = new ArrayList[rows]; // Tableau de lignes

        // Initialisation de la grille avec des cellules vides
        for (int i = 0; i < rows; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(" "); // Ajouter une cellule vide
            }
            grille[i] = row;
        }

        grille[1].set(0, player.getNAME());

        // Création des NPC
        String[] staticNPC = { "M", "A", "R", "D" };
        // Générer un nombre aléatoire de NPC entre 2 et 4
        int nombrePersonnages = r.nextInt(3) + 2;

        for (int i = 0; i < nombrePersonnages; i++) { // Positionner les NPC aléatoirement
            int posX, posY;
            do {
                posX = r.nextInt(rows);
                posY = r.nextInt(cols);
            } while (!grille[posX].get(posY).equals(" ")); // Vérifie si la cellule est déjà occupée

            grille[posX].set(posY, staticNPC[i]); // Place un staticNPC

            // Création dynamique et aléatoire
            // NPC npc = new NPC(posX,posY) // Création d'un NPC justa avec les co, le reste random
            // grille[npc.posX].set(npc.posY, npc.NAME.charAt(0));
        }

        return grille; // Retourne la matrice créée
    }

    // Méthode pour afficher la grille
    public void afficherMatrice() {
        System.out.println("Positionnement des personnages dans "+getNom());
        for (ArrayList<String> row : grille) {
            System.out.println(row);
        }
    }

    // Vérifie si la salle contient encore au moins un NPC
    public boolean contientNPC() {
        for (ArrayList<String> row : grille) { // Parcourt chaque ligne de la grille
            for (String cell : row) { // Parcourt chaque cellule de la ligne
                // Vérifie si la cellule contient un caractère qui n'est pas un espace
                if (!cell.equals(" ") && !cell.equals("M")) {
                    return true; // Retourne vrai si un NPC est trouvé
                }
            }
        }
        return false; // Retourne faux si aucune cellule n'a de NPC
    }

    // Vide la salle de ses NPCs sauf "M"
    public void viderSalleSaufM() {
        for (ArrayList<String> row : grille) { // Parcourt chaque ligne de la grille
            for (int i = 0; i < row.size(); i++) { // Parcourt chaque cellule de la ligne
                // Si la cellule ne contient pas "M" et n'est pas vide
                if (!row.get(i).equals(" ") && !row.get(i).equals("M")) {
                    row.set(i, " "); // Remplace le contenu par une cellule vide
                }
            }
        }
    }

    // Permet de mettre en avant des personnages
    public void afficherMatriceAvecCouleurs() {

        // Codes ANSI pour les couleurs
        final String RESET = "\u001B[0m";   // Reset color
        final String BLUE = "\u001B[34m";   // Blue color, sera utilisé pour le main character
        final String RED = "\u001B[31m";    // Red color, pourra être utilisé pour un Boss

        System.out.println("Positionnement des personnages dans " + getNom());

        for (ArrayList<String> row : grille) {
            StringBuilder coloredRow = new StringBuilder("["); // Commence une ligne formatée

            for (int i = 0; i < row.size(); i++) {
                String cell = row.get(i);

                // Applique des couleurs aux personnages spécifiques
                if (cell.equals("M")) {
                    coloredRow.append(BLUE).append(cell).append(RESET);
                } else {
                    coloredRow.append(cell); // Cellule vide ou non colorée
                }

                // Ajoute une virgule si ce n'est pas la dernière cellule
                if (i < row.size() - 1) {
                    coloredRow.append(", ");
                }
            }

            coloredRow.append("]"); // Termine la ligne formatée
            System.out.println(coloredRow); // Affiche la ligne
        }
    }
    //hello
}

