package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test unitaire pour la classe {@link Player}.
 * Utilise JUnit pour tester les fonctionnalités principales de la classe Player.
 */
public class PlayerTest {

    private Player player;

    /**
     * Prépare un objet Player avant chaque test.
     * Cette méthode est exécutée avant chaque méthode de test pour initialiser un objet Player.
     */
    @Before
    public void setUp() {
        player = new Player("TestPlayer");
    }

    /**
     * Teste le constructeur et les accesseurs de la classe {@link Player}.
     * Vérifie que les valeurs initiales des attributs correspondent aux attentes.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(300, player.getHP_MAX());
        assertEquals(300, player.getHp());
        assertEquals(20, player.getDEF());
        assertEquals(50, player.getATK());
        assertEquals(2, player.getRange());
        assertEquals(0, player.getPosX());
        assertEquals(1, player.getPosY());
    }

    /**
     * Teste les méthodes de modification des attributs (setters) de la classe {@link Player}.
     * Vérifie que les valeurs modifiées des attributs correspondent aux attentes.
     */
    @Test
    public void testSetters() {
        player.setHp(80);
        player.setDEF(20);
        player.setATK(30);
        player.setRange(5);
        player.setPosX(3);
        player.setPosY(4);

        assertEquals(80, player.getHp());
        assertEquals(20, player.getDEF());
        assertEquals(30, player.getATK());
        assertEquals(5, player.getRange());
        assertEquals(3, player.getPosX());
        assertEquals(4, player.getPosY());
    }

    /**
     * Teste la méthode {@link Player#takeDamage(int)}.
     * Vérifie que le joueur perd correctement des points de vie lorsqu'il subit des dégâts.
     */
    @Test
    public void testTakeDammage() {
        Player tom = new Player("tom");
        tom.takeDamage(10);
        int damageTaken = (int) (10 * (1 - (20 / (20 + 50.0))));
        assertEquals(300 - damageTaken, tom.getHp());

        // player.takeDamage(1000);
        // assertEquals(1000 - (10 * (1 - 10/(10+50))), player.getHp()); // Gère les HP négatifs (optionnel)
    }

    /**
     * Teste la méthode {@link Player#heal(int)}.
     * Vérifie que le joueur regagne des points de vie sans dépasser la limite maximale (HP_MAX).
     */
    @Test
    public void testHeal() {
        player.setHp(50);
        player.heal(30);
        assertEquals(80, player.getHp());

        player.heal(50); // Ne peut pas dépasser le maximum
        assertEquals(130, player.getHp());
    }

    /**
     * Teste la méthode {@link Player#isAlive()}.
     * Vérifie que la méthode retourne correctement si le joueur est en vie ou non.
     */
    @Test
    public void testIsAlive() {
        assertTrue(player.isAlive());
        player.takeDamage(1000);
        assertFalse(player.isAlive());
    }

    /**
     * Teste la méthode {@link Player#attack(int, Player)} lorsque la cible est à portée.
     * Vérifie que les points de vie de la cible diminuent correctement après l'attaque.
     */
    @Test
    public void testAttackWithinRange() {
        Player target = new Player("TargetPlayer");
        target.setPosX(1);
        target.setPosY(1);

        player.setPosX(0);
        player.setPosY(0);
        player.attack(10, target);

        int damageTaken = (int) (10 * (1 - (20 / (20 + 50.0))));
        assertEquals(300-damageTaken, target.getHp()); // La cible reçoit les dégâts
    }

    /**
     * Teste la méthode {@link Player#attack(int, Player)} lorsque la cible est hors de portée.
     * Vérifie que les points de vie de la cible restent inchangés si elle est hors de portée.
     */
    @Test
    public void testAttackOutOfRange() {
        Player target = new Player("TargetPlayer");
        target.setPosX(10);
        target.setPosY(10);

        player.setPosX(0);
        player.setPosY(0);
        player.attack(10, target);

        assertEquals(300, target.getHp()); // La cible ne reçoit aucun dégât
    }

    /**
     * Teste la méthode {@link Player#shield()}.
     * Vérifie que la défense (DEF) du joueur augmente correctement après activation du bouclier.
     */
    @Test
    public void testShield() {
        player.shield();
        assertEquals(25, player.getDEF());
    }
}
