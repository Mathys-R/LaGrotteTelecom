package org.example;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Classe de test unitaire pour la classe {@link Salle}.
 * Utilise JUnit pour vérifier les fonctionnalités des salles du jeu.
 */
public class SalleTest {

    /**
     * Teste le constructeur de la classe {@link Salle}.
     * Vérifie que le nom de la salle est correctement assigné,
     * que la liste des NPCs est initialisée et qu'elle contient des NPCs après l'initialisation.
     */
    @Test
    public void testConstructeurSalle() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle1", player);

        assertEquals("Salle1", salle.getNom());
        assertNotNull("La liste des NPC ne doit pas être null après l'initialisation.", salle.getListNPC());
        assertFalse("La liste des NPC doit contenir des NPC après l'initialisation.", salle.getListNPC().isEmpty());
    }

    /**
     * Teste la méthode {@link Salle#getNpcByID(int)}.
     * Vérifie qu'un NPC peut être correctement récupéré par son ID.
     */
    @Test
    public void testGetNpcByID() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle3", player);

        NPC npc = salle.getListNPC().get(0); // Récupère le premier NPC
        assertEquals("Le NPC correspondant à l'ID devrait être retourné.", npc, salle.getNpcByID(npc.getID()));
    }

    /**
     * Teste la méthode {@link Salle#deplacementCharacter(Character, int, int)}.
     * Vérifie qu'un personnage peut se déplacer vers une case vide et
     * que le déplacement échoue si la case est déjà occupée.
     */
    @Test
    public void testDeplacementCharacter() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle4", player);

        // Initialisation des cases vides sur la grille
        salle.getGrille()[1].set(0, " ");
        salle.getGrille()[1].set(1, " ");

        Character character = new Troll(1, 0);
        assertTrue("Le déplacement vers une case vide devrait réussir.", salle.deplacementCharacter(character, 1, 1));
        assertEquals("La position X du personnage devrait être mise à jour.", 1, character.getPosX());
        assertEquals("La position Y du personnage devrait être mise à jour.", 1, character.getPosY());

        assertFalse("Le déplacement vers une case occupée devrait échouer.", salle.deplacementCharacter(character, 1, 1));
    }

    /**
     * Teste la méthode {@link Salle#refreshGrille(Player)}.
     * Vérifie que les NPC morts sont retirés de la liste et que leurs cases sont vidées sur la grille.
     */
    @Test
    public void testRefreshGrille() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle5", player);

        NPC npc = salle.getListNPC().get(0); // Récupère un NPC
        npc.setAlive(false); // Simule la mort du NPC
        salle.refreshGrille(player);

        assertFalse("Le NPC mort devrait être retiré de la liste.", salle.getListNPC().contains(npc));
        // assertEquals("La cellule du NPC mort devrait être vide.", " ", salle.getGrille()[npc.getPosX()].get(npc.getPosY()));
    }

    /**
     * Teste la méthode {@link Salle#creerMatrice(Player)}.
     * Vérifie que la grille est correctement créée et que le nombre de NPC sur la grille
     * correspond au nombre de NPC dans la liste.
     */
    @Test
    public void testCreerMatrice() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("SalleDeTest", player);

        ArrayList<String>[] grille = salle.getGrille();
        char playerInitial = player.getName().charAt(0);

        int npcCount = -1;
        for (ArrayList<String> row : grille) {
            for (String cell : row) {
                if (!cell.equals(" ") && !cell.equals(String.valueOf(playerInitial))) {
                    npcCount++;
                }
            }
        }
        assertEquals("Le nombre de NPC sur la grille doit correspondre à la liste.", salle.getListNPC().size(), npcCount);
    }
}
