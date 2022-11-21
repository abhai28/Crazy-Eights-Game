package com.example.comp4004f22a3101077008;

import junit.framework.TestCase;

public class CardTest extends TestCase {
    Card c = new Card("H","A");
    public void testGetSuit(){
        assertEquals("H",c.getSuit());
    }
}
