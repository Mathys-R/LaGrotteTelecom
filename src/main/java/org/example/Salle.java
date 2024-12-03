package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;



class Salle {
    private String nom;
    private int ameliorationAttaque;
    private int ameliorationDefense;
    private ArrayList<String>[] grille; // Tableau de lignes (ArrayList<String>)
    private static final Logger logger = LogControler.getLogger();

    @SuppressWarnings("unchecked")
    public Salle(String nom, int ameliorationAttaque, int ameliorationDefense) {
        this.nom = nom;
        logger.info("Création d'une nouvelle salle : " + nom);
        this.ameliorationAttaque = ameliorationAttaque;
        this.ameliorationDefense = ameliorationDefense;
        grille = creerMatrice(); // Génération de la grille
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
        return "Salle: " + nom +
                " | Amélioration Attaque: " + ameliorationAttaque +
                " | Amélioration Défense: " + ameliorationDefense;
    }

    // Génère une matrice sous forme d'ArrayList<String>[]
    @SuppressWarnings("unchecked")
    private static ArrayList<String>[] creerMatrice() {
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
            // NPC npc = creerNPCAleatoire(posX,posY); // Création d'un NPC justa avec les co, le reste random
            // grille[npc.posX].set(npc.posY, npc.NAME.charAt(0));
        }

        return grille; // Retourne la matrice créée
    }

    // Création de NPC aléatoirement pour avoir une diversité de NPC
    public static NPC creerNPCAleatoire(int posX, int posY) {
        Random random = new Random();
        int type = random.nextInt(3); // Génère un entier aléatoire entre 0 et 2

        switch (type) {
            case 0:
                return new Troll(posX, posY); // Retourne un Troll
            case 1:
                return new Orc(posX, posY); // Retourne un Orc
            case 2:
                return new Goblin(posX, posY); // Retourne un Goblin
            default:
                throw new IllegalStateException("Valeur inattendue : " + type);
        }
    }

    // Méthode pour vérifier si la grille est vide
    public boolean matriceIsEmpty() {
        for (ArrayList<String> row : grille) {
            for (String cell : row) {
                if (!cell.equals(" ")) { // Si une cellule n'est pas vide, la grille n'est pas vide
                    return false;
                }
            }
        }
        return true; // Si aucune cellule n'est occupée, la grille est vide
    }

    // Méthode pour afficher la grille
    public void afficherMatrice() {
        System.out.println("Positionnement des personnages dans "+getNom());
        for (ArrayList<String> row : grille) {
            System.out.println(row);
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
}

