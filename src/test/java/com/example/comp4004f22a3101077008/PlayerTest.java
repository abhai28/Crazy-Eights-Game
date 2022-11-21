package com.example.comp4004f22a3101077008;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    Player p = new Player(1);
    public void testGetID(){
        assertEquals(1,p.getID());
    }
}
