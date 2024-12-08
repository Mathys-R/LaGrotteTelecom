package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

class Salle {
    private String nom;
    private int ameliorationAttaque;
    private int ameliorationDefense;
    private ArrayList<String>[] grille; // Tableau de lignes (ArrayList<String>)
    private ArrayList<NPC> listNPC;
    private int sizeX;
    private int sizeY;

    // Utilisation du logger de LogControler
    private static final Logger logger = LogControler.getLogger();

    public Salle(String nom, int ameliorationAttaque, int ameliorationDefense, Player player) {
        this.nom = nom;
        this.ameliorationAttaque = ameliorationAttaque;
        this.ameliorationDefense = ameliorationDefense;
        this.listNPC = new ArrayList<>();
        grille = creerMatrice(player); // Génération de la grille
        logger.info("Salle '" + nom + "' créée avec succès.");
    }

    public String getNom() {
        return nom;
    }

    public int getAmeliorationAttaque() {
        return ameliorationAttaque;
    }

    public int getAmeliorationDefense() {
        return ameliorationDefense;
    }

    public ArrayList<String>[] getGrille() {
        return grille;
    }

    @Override
    public String toString() {
        return nom +
                " | Amélioration Attaque: " + ameliorationAttaque +
                " | Amélioration Défense: " + ameliorationDefense;
    }

    public void showNPC() {
        for (NPC mob : listNPC) {
            System.out.println(mob);
            logger.info("Affichage du NPC: " + mob);
        }
    }

    public NPC getNpcByID(int ID) {
        for (NPC mob : listNPC) {
            if (mob.getID() == ID) {
                return mob;
            }
        }
        return null;
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

    public ArrayList<NPC> getListNPC() {
        return listNPC;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<String>[] creerMatrice(Player player) {
        Random r = new Random();

        // Dimensions de la grille
        int rows = 3;
        int cols = 6 + r.nextInt(3);

        this.sizeX = cols;
        this.sizeY = rows;

        ArrayList<String>[] grille = new ArrayList[rows]; // Tableau de lignes

        // Initialisation de la grille avec des cellules vides
        for (int i = 0; i < rows; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(" "); // Ajouter une cellule vide
            }
            grille[i] = row;
        }

        grille[1].set(0, String.valueOf(player.getName().charAt(0)));

        // Générer un nombre aléatoire de NPC entre 2 et 4
        int nombrePersonnages = r.nextInt(3) + 2;

        for (int i = 0; i < nombrePersonnages; i++) { // Positionner les NPC aléatoirement
            int posX, posY;
            do {
                posX = r.nextInt(rows);
                posY = r.nextInt(cols);
            } while (!grille[posX].get(posY).equals(" ")); // Vérifie si la cellule est déjà occupée

            // Création dynamique et aléatoire
            NPC npc = creerNPCAleatoire(posX, posY); // Création d'un NPC juste avec les co, le reste random
            final String RESET = "\u001B[0m"; // Reset color
            final String RED = "\u001B[31m"; // RED color, sera utilisé pour les NPC méchants

            grille[npc.getPosX()].set(npc.getPosY(), RED + npc.getName().charAt(0) + RESET);
            listNPC.add(npc);

            // Log l'ajout du NPC à la grille
            logger.info(
                    "NPC ajouté: " + npc.getName() + " à la position (" + npc.getPosX() + ", " + npc.getPosY() + ")");
        }

        return grille; // Retourne la matrice créée
    }

    public Boolean deplacementCharacter(Character target, int newPosX, int newPosY) {
        if (newPosX < this.sizeX && newPosX >= 0 && newPosY >= 0 && newPosY < this.sizeY) {
            if (grille[newPosY].get(newPosX).equals(" ")) { // Case vide
                grille[newPosY].set(newPosX, String.valueOf(target.getName().charAt(0))); // Déplace le caractère
                grille[target.getPosX()].set(target.getPosY(), " "); // Libère l'ancienne case
                target.setPosX(newPosX);
                target.setPosY(newPosY);
                logger.info(target.getName() + " a été déplacé vers (" + newPosX + ", " + newPosY + ")");
                return true;
            } else {
                logger.warning("Déplacement échoué: Case (" + newPosX + ", " + newPosY + ") déjà occupée.");
                return false;
            }
        } else {
            return false;
        }
    }

    // Méthode pour afficher la grille
    public void afficherMatrice() {
        refreshGrille();
        logger.info("Affichage de la grille pour la salle: " + getNom());
        System.out.println("\nPositionnement des personnages dans " + getNom());
        for (ArrayList<String> row : grille) {
            System.out.println(row);
        }
    }

    public void refreshGrille() {
        // Parcourir la liste des NPC pour trouver ceux qui sont morts
        ArrayList<NPC> npcsMorts = new ArrayList<>();
        for (NPC npc : listNPC) {
            if (!npc.isAlive()) { // Vérifie si le NPC est mort
                npcsMorts.add(npc);
            }
        }
        // Supprimer les NPC morts de la liste et de la grille
        for (NPC mort : npcsMorts) {
            grille[mort.getPosX()].set(mort.getPosY(), " ");
            listNPC.remove(mort); // Supprimer le mort de la liste des NPC
            logger.info(mort.getName() + " est mort et a été retiré de la grille.");
        }
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

}
