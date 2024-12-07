package org.example;

import org.junit.Test;
import static org.junit.Assert.*;



public class PlayerTest {
    @Test
    public void testTakeDammage(){
        Player tom = new Player(100, 0,0,"tom",0,0,0);
        tom.takeDamage(10);
        assertEquals(90,tom.getHP());
    }
}
