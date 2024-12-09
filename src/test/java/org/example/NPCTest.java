package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la classe abstraite {@link NPC}.
 * Utilise JUnit pour vérifier les fonctionnalités principales des instances de NPC.
 */
public class NPCTest {

    /**
     * Teste le constructeur et les méthodes d'accès de la classe {@link NPC}.
     * Vérifie que tous les attributs sont correctement initialisés et accessibles.
     */
    @Test
    public void testConstructorAndGetters() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        assertEquals("Goblin", npc.getName());
        assertEquals(100, npc.getHp());
        assertEquals(10, npc.getDEF());
        assertEquals(15, npc.getATK());
        assertEquals(5, npc.getRange());
        assertEquals(2, npc.getPosX());
        assertEquals(3, npc.getPosY());
        assertTrue(npc.getID() > 0, "L'ID doit être un entier positif.");
    }

    /**
     * Teste les méthodes mutatrices (setters) de la classe {@link NPC}.
     * Vérifie que tous les attributs peuvent être modifiés correctement.
     */
    @Test
    public void testSetters() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.setName("Orc");
        npc.setHp(80);
        npc.setDEF(12);
        npc.setATK(18);
        npc.setRange(6);
        npc.setPosX(5);
        npc.setPosY(6);

        assertEquals("Orc", npc.getName());
        assertEquals(80, npc.getHp());
        assertEquals(12, npc.getDEF());
        assertEquals(18, npc.getATK());
        assertEquals(6, npc.getRange());
        assertEquals(5, npc.getPosX());
        assertEquals(6, npc.getPosY());
    }

    /**
     * Teste la méthode {@link NPC#takeDamage(int)}.
     * Vérifie que les dégâts réduisent correctement les HP du NPC sans dépasser 0.
     */
    @Test
    public void testTakeDamage() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.takeDamage(20);
        assertEquals(100 - (20 * (1 - 10/(10+50))), npc.getHp());
        npc.takeDamage(80);
        assertEquals(0, npc.getHp(), "Les HP ne doivent pas devenir négatifs.");
    }

    /**
     * Teste la méthode {@link NPC#heal(int)}.
     * Vérifie que les HP sont restaurés correctement sans dépasser la valeur maximale.
     */
    @Test
    public void testHeal() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.takeDamage(50);
        npc.heal(30);
        assertEquals(80, npc.getHp());
        npc.heal(50);
        assertEquals(100, npc.getHp(), "Les HP ne doivent pas dépasser hpMax.");
    }

    /**
     * Teste la méthode {@link NPC#shield()}.
     * Vérifie que la défense (DEF) du NPC augmente correctement.
     */
    @Test
    public void testShield() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.shield();
        assertEquals(15, npc.getDEF());
    }

    /**
     * Teste la méthode {@link NPC#attack(int, NPC)} lorsque la cible est à portée.
     * Vérifie que l'attaque réduit correctement les HP de la cible.
     */
    @Test
    public void testAttackWithinRange() {
        NPC target = new NPC("Elf", 100, 5, 10, 3, 4, 4) {};
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};

        npc.attack(20, target);
        assertEquals(80, target.getHp());
    }

    /**
     * Teste la méthode {@link NPC#attack(int, NPC)} lorsque la cible est hors de portée.
     * Vérifie qu'aucune attaque ne se produit si la cible est trop éloignée.
     */
    @Test
    public void testAttackOutOfRange() {
        NPC target = new NPC("Elf", 100, 5, 10, 3, 10, 10) {};
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};

        npc.attack(20, target);
        assertEquals(100, target.getHp(), "Aucune attaque ne doit être effectuée si la cible est hors de portée.");
    }

    /**
     * Teste la méthode {@link NPC#isAlive()}.
     * Vérifie que la méthode retourne correctement si le NPC est vivant ou non.
     */
    @Test
    public void testIsAlive() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        assertTrue(npc.isAlive());
        npc.setHp(0);
        assertFalse(npc.isAlive());
    }

    /**
     * Teste la méthode {@link NPC#setAlive(boolean)}.
     * Vérifie que la propriété vivant (alive) peut être correctement définie et ses effets.
     */
    @Test
    public void testSetAlive() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        npc.setAlive(false);
        assertEquals(0, npc.getHp());
        assertFalse(npc.isAlive());

        npc.setAlive(true);
        assertEquals(1, npc.getHp());
        assertTrue(npc.isAlive());
    }

    /**
     * Teste la méthode {@link NPC#toString()}.
     * Vérifie que la représentation textuelle du NPC est correcte.
     */
    @Test
    public void testToString() {
        NPC npc = new NPC("Goblin", 100, 10, 15, 5, 2, 3) {};
        String expected = "Goblin{ID=" + npc.getID() + ", HP_MAX=100, hp=100, DEF=10, ATK=15, range=5, posX=2, posY=3}";
        assertEquals(expected, npc.toString());
    }
}
