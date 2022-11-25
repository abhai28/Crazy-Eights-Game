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
    @Test
    void testDealCards(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.players.add(p);
        }

        assertFalse(game.dealCards());
        game.populateDeck();
        game.shuffleDeck();
        assertTrue(game.dealCards());
        for(Player p :game.players){
            assertEquals(5,p.handSize());
        }
    }
}
