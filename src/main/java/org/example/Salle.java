package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Classe représentant une salle dans l'ensemble de la carte du jeu
 * Une salle contient une grille avec plusieurs NPCs, et des informations de positionnement.
 */
public class Salle {

    private String nom;
    private ArrayList<String>[] grille; // Tableau représentant la grille de la salle
    private ArrayList<NPC> listNPC; // Liste des NPC présents dans la salle
    private int sizeX; // Largeur de la grille
    private int sizeY; // Hauteur de la grille

    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur de la classe Salle.
     *
     * @param nom                Nom de la salle.
     * @param player             Joueur initialement positionné dans la salle.
     */
    public Salle(String nom, Player player) {
        this.nom = nom;
        this.listNPC = new ArrayList<>();
        grille = creerMatrice(player);
        logger.info("Salle '" + nom + "' créée avec succès.");
    }

    /**
     * Retourne le nom de la salle.
     *
     * @return Nom de la salle.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la grille de la salle.
     *
     * @return Grille de la salle sous forme de tableau de listes.
     */
    public ArrayList<String>[] getGrille() {
        return grille;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la salle.
     *
     * @return Nom de la salle.
     */
    @Override
    public String toString() {
        return nom;
    }

    /**
     * Affiche la liste des NPCs présents dans la salle.
     */
    public void showNPC() {
        for (NPC mob : listNPC) {
            System.out.println(mob);
            logger.info("Affichage du NPC: " + mob);
        }
    }

    /**
     * Retourne un NPC en fonction de son identifiant unique.
     *
     * @param ID Identifiant du NPC recherché.
     * @return NPC correspondant à l'identifiant ou null si non trouvé.
     */
    public NPC getNpcByID(int ID) {
        for (NPC mob : listNPC) {
            if (mob.getID() == ID) {
                return mob;
            }
        }
        return null;
    }

    /**
     * Vérifie si la salle contient des NPCs autres que le joueur.
     *
     * @param player Joueur dont la présence doit être exclue de la recherche.
     * @return true si des NPCs sont présents, false sinon.
     */
    public boolean contientNPC(Player player) {
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String BLUE = "\u001B[34m";

        String joueurCell = BLUE + player.getName().charAt(0) + RESET;

        for (ArrayList<String> row : grille) {
            for (String cell : row) {
                String cellClean = cell.replace(RESET, "").replace(RED, "").replace(BLUE, "");

                if (!cellClean.equals(" ") && !cell.equals(joueurCell)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourne la liste des NPCs présents dans la salle.
     *
     * @return Liste des NPCs.
     */
    public ArrayList<NPC> getListNPC() {
        return listNPC;
    }

    /**
     * Crée une matrice représentant la salle et positionne les NPCs et le joueur.
     *
     * @param player Joueur à positionner dans la grille.
     * @return Matrice représentant la salle.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String>[] creerMatrice(Player player) {
        Random r = new Random();
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String BLUE = "\u001B[34m";

        int rows = 3;
        int cols = 6 + r.nextInt(3);

        this.sizeX = cols;
        this.sizeY = rows;

        ArrayList<String>[] grille = new ArrayList[rows];

        for (int i = 0; i < rows; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(" ");
            }
            grille[i] = row;
        }

        grille[1].set(0, BLUE + player.getName().charAt(0) + RESET);

        int nombrePersonnages = r.nextInt(2) + 2;

        for (int i = 0; i < nombrePersonnages; i++) {
            int posX, posY;
            do {
                posX = r.nextInt(cols);
                posY = r.nextInt(rows);
            } while (!grille[posY].get(posX).equals(" "));

            NPC npc = creerNPCAleatoire(posX, posY);
            grille[npc.getPosY()].set(npc.getPosX(), RED + npc.getName().charAt(0) + RESET);
            listNPC.add(npc);

            logger.info("NPC ajouté: " + npc.getName() + " à la position (" + npc.getPosX() + ", " + npc.getPosY() + ")");
        }

        return grille;
    }

    /**
     * Déplace un personnage vers une nouvelle position dans la salle.
     *
     * @param target  Personnage à déplacer.
     * @param newPosX Nouvelle position X.
     * @param newPosY Nouvelle position Y.
     * @return true si le déplacement a réussi, false sinon.
     */
    public Boolean deplacementCharacter(Character target, int newPosX, int newPosY) {
        if (newPosX < this.sizeX && newPosX >= 0 && newPosY >= 0 && newPosY < this.sizeY) {
            if (grille[newPosY].get(newPosX).equals(" ")) {
                grille[newPosY].set(newPosX, String.valueOf(target.getName().charAt(0)));
                grille[target.getPosY()].set(target.getPosX(), " ");
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

    /**
     * Tue tous les NPCs présents dans la salle en réduisant leurs points de vie à zéro.
     */
    public void killAll() {
        for (NPC mob : listNPC) {
            mob.setHp(mob.getHp() - mob.getHP_MAX());
        }
    }

    /**
     * Affiche la grille de la salle, incluant les positions des personnages.
     *
     * @param player Joueur dont la position est également affichée dans la grille.
     */
    public void afficherMatrice(Player player) {
        logger.info("Affichage de la grille pour la salle: " + getNom());
        System.out.println("\nPositionnement des personnages dans " + getNom());
        for (ArrayList<String> row : grille) {
            System.out.println(row);
        }
    }

    /**
     * Actualise la grille de la salle pour refléter les changements d'état des personnages.
     * Met à jour les positions des NPCs et du joueur, et retire les NPCs morts.
     *
     * @param player Joueur dont la position est mise à jour.
     */
    public void refreshGrille(Player player) {
        final String RESET = "\u001B[0m"; // Reset color
        final String RED = "\u001B[31m"; // RED color
        final String BLUE = "\u001B[34m"; // BLUE color

        logger.info("Rafraîchissement de la grille pour la salle: " + getNom());

        grille[player.getPosY()].set(player.getPosX(), BLUE + player.getName().charAt(0) + RESET);

        // Parcourir la liste des NPC pour actualiser leurs positions
        ArrayList<NPC> npcsMorts = new ArrayList<>();
        for (NPC npc : listNPC) {
            // Vérifie si le NPC est mort
            if (!npc.isAlive()) {
                npcsMorts.add(npc);
            } else {
                grille[npc.getPosY()].set(npc.getPosX(), RED + npc.getName().charAt(0) + RESET);
            }
        }

        for (NPC mort : npcsMorts) {
            // Vider la case où était présent le NPC
            grille[mort.getPosY()].set(mort.getPosX(), " ");
            listNPC.remove(mort);
            logger.info(mort.getName() + " est mort et a été retiré de la grille.");
        }
    }

    /**
     * Crée un NPC aléatoirement parmi les types disponibles : Troll, Orc, ou Goblin.
     *
     * @param posX Position X du NPC dans la grille.
     * @param posY Position Y du NPC dans la grille.
     * @return Un NPC nouvellement créé, de type aléatoire.
     */
    public static NPC creerNPCAleatoire(int posX, int posY) {
        Random random = new Random();
        int type = random.nextInt(3);

        switch (type) {
            case 0:
                return new Troll(posX, posY);
            case 1:
                return new Orc(posX, posY);
            case 2:
                return new Goblin(posX, posY);
            default:
                throw new IllegalStateException("Valeur inattendue : " + type);
        }
    }
}
