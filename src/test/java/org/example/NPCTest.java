package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NPCTest {

    @Test
    public void testConstructorAndGetters() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        assertEquals("Goblin", npc.getName());
        assertEquals(100, npc.getHP());
        assertEquals(10, npc.getDEF());
        assertEquals(15, npc.getATK());
        assertEquals(5, npc.getRange());
        assertEquals(2, npc.getPosX());
        assertEquals(3, npc.getPosY());
        assertTrue(npc.getID() > 0, "L'ID doit être un entier positif.");
    }

    @Test
    public void testSetters() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.setName("Orc");
        npc.setHP(80);
        npc.setDEF(12);
        npc.setATK(18);
        npc.setRange(6);
        npc.setPosX(5);
        npc.setPosY(6);

        assertEquals("Orc", npc.getName());
        assertEquals(80, npc.getHP());
        assertEquals(12, npc.getDEF());
        assertEquals(18, npc.getATK());
        assertEquals(6, npc.getRange());
        assertEquals(5, npc.getPosX());
        assertEquals(6, npc.getPosY());
    }

    @Test
    public void testTakeDamage() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.takeDamage(20);
        assertEquals(80, npc.getHP());
        npc.takeDamage(80);
        assertEquals(0, npc.getHP(), "Les HP ne doivent pas devenir négatifs.");
    }

    @Test
    public void testHeal() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.takeDamage(50);
        npc.heal(30);
        assertEquals(80, npc.getHP());
        npc.heal(50);
        assertEquals(100, npc.getHP(), "Les HP ne doivent pas dépasser hpMax.");
    }

    @Test
    public void testShield() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.shield();
        assertEquals(15, npc.getDEF());
    }

    @Test
    public void testAttackWithinRange() {
        NPC target = new NPC("Elf", 100, 5, 10, 3, 4, 4) {};
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};

        npc.attack(20, target);
        assertEquals(80, target.getHP());
    }

    @Test
    public void testAttackOutOfRange() {
        NPC target = new NPC("Elf", 100, 5, 10, 3, 10, 10) {};
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};

        npc.attack(20, target);
        assertEquals(100, target.getHP(), "Aucune attaque ne doit être effectuée si la cible est hors de portée.");
    }

    @Test
    public void testIsAlive() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        assertTrue(npc.isAlive());
        npc.setHP(0);
        assertFalse(npc.isAlive());
    }

    @Test
    public void testSetAlive() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.setAlive(false);
        assertEquals(0, npc.getHP());
        assertFalse(npc.isAlive());

        npc.setAlive(true);
        assertEquals(1, npc.getHP());
        assertTrue(npc.isAlive());
    }

    @Test
    public void testToString() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        String expected = "Goblin{ID=" + npc.getID() + ", hpMax=100, HP=100, DEF=10, ATK=15, range=5, posX=2, posY=3}";
        assertEquals(expected, npc.toString());
    }
}
