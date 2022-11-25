package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game = new Game();
    @Test
    void testPopulateDeck(){
        game.populateDeck();
        assertEquals(52,game.cards.size());
    }
    @Test
    void testShuffleDeck(){
        game.populateDeck();
        String r = game.cards.get(0).getRank();
        game.shuffleDeck();
        assertNotEquals(r,game.cards.get(0).getRank());
    }
    
}
