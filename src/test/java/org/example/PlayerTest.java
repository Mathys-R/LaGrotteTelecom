package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("TestPlayer");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(100, player.getHPMax());
        assertEquals(100, player.getHP());
        assertEquals(10, player.getDEF());
        assertEquals(15, player.getATK());
        assertEquals(2, player.getRange());
        assertEquals(0, player.getPosX());
        assertEquals(0, player.getPosY());
    }

    @Test
    public void testSetters() {
        player.setHP(80);
        player.setDEF(20);
        player.setATK(30);
        player.setRange(5);
        player.setPosX(3);
        player.setPosY(4);

        assertEquals(80, player.getHP());
        assertEquals(20, player.getDEF());
        assertEquals(30, player.getATK());
        assertEquals(5, player.getRange());
        assertEquals(3, player.getPosX());
        assertEquals(4, player.getPosY());
    }

    @Test
    public void testTakeDammage() {
        Player tom = new Player("tom");
        tom.takeDamage(10);
        assertEquals(100 - (10 * (1 - 10/(10+50))), tom.getHP());

        player.takeDamage(1000);
        assertEquals(1000 - (10 * (1 - 10/(10+50)), player.getHP()); // Gère les HP négatifs (optionnel)
    }

    @Test
    public void testHeal() {
        player.setHP(50);
        player.heal(30);
        assertEquals(80, player.getHP());

        player.heal(50); // Ne peut pas dépasser le maximum
        assertEquals(100, player.getHP());
    }

    @Test
    public void testIsAlive() {
        assertTrue(player.isAlive());
        player.takeDamage(100);
        assertFalse(player.isAlive());
    }

    @Test
    public void testAttackWithinRange() {
        Player target = new Player("TargetPlayer");
        target.setPosX(1);
        target.setPosY(1);

        player.setPosX(0);
        player.setPosY(0);
        player.attack(10, target);

        assertEquals(90, target.getHP()); // La cible reçoit les dégâts
    }

    @Test
    public void testAttackOutOfRange() {
        Player target = new Player("TargetPlayer");
        target.setPosX(10);
        target.setPosY(10);

        player.setPosX(0);
        player.setPosY(0);
        player.attack(10, target);

        assertEquals(100, target.getHP()); // La cible ne reçoit aucun dégât
    }

    @Test
    public void testShield() {
        player.shield();
        assertEquals(15, player.getDEF());
    }
}