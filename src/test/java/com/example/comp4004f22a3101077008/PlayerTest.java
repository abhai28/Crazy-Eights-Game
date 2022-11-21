package com.example.comp4004f22a3101077008;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    Player p = new Player(1);
    public void testGetID(){
        assertEquals(1,p.getID());
    }
    public void testAddCard(){
        Card c = new Card("H","A");
        p.addCard(c);
        assertEquals("H",p.getCard(0).getSuit());
    }
    public void testSetScore(){
        p.setScore(15);
        assertEquals(15,p.getScore());
    }
    public void testAddScore(){
        p.addScore(5);
        assertEquals(5,p.getScore());
    }
}
