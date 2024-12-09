package org.example;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;



public class SalleTest {

    @Test
    public void testConstructeurSalle() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle1", 10, 5, player);

        assertEquals("Salle1", salle.getNom());
        assertNotNull(salle.getListNPC());
        assertFalse("La liste des NPC doit contenir des NPC après l'initialisation.", salle.getListNPC().isEmpty());
    }
    @Test
    public void testGetNpcByID() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle3", 0, 0, player);

        NPC npc = salle.getListNPC().get(0); // Prenez le premier NPC
        assertEquals("Le NPC correspondant à l'ID devrait être retourné.", npc, salle.getNpcByID(npc.getID()));
    }
    @Test
    public void testDeplacementCharacter() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle4", 0, 0, player);

        // On s'assure que (1, 0) et (1, 1) sont vides
        salle.getGrille()[1].set(0, " ");
        salle.getGrille()[1].set(1, " ");

        Character character = new Troll(1, 0);
        assertTrue("Le déplacement vers une case vide devrait réussir.", salle.deplacementCharacter(character, 1, 1));
        assertEquals(1, character.getPosX());
        assertEquals(1, character.getPosY());

        assertFalse("Le déplacement vers une case occupée devrait échouer.", salle.deplacementCharacter(character, 1, 1));
    }
    @Test
    public void testRefreshGrille() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle5", 0, 0, player);

        NPC npc = salle.getListNPC().get(0); // Prenez un NPC
        npc.setAlive(false); // Simulez la mort du NPC
        salle.refreshGrille(player);

        assertFalse("Le NPC mort devrait être retiré de la liste.", salle.getListNPC().contains(npc));
        assertEquals("La cellule du NPC mort devrait être vide.", salle.getGrille()[npc.getPosX()].get(npc.getPosY()), " ");
    }
    @Test
    public void testCreerMatrice() {
        Player player = new Player("PlayerName");
        Salle salle = new Salle("Salle6", 0, 0, player);

        ArrayList<String>[] grille = salle.getGrille();
        char playerInitial = player.getName().charAt(0);

        int npcCount = 0;
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
