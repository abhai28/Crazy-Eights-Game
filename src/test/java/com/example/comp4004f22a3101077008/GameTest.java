package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameTest {
    Game game = new Game();
    @Test
    void testPopulateDeck(){
        game.populateDeck();
        assertEquals(52,game.cards.size());
    }
    @Test
    void testShuffleDeck(){
        ArrayList<Card> testCard = game.cards;
        game.shuffleDeck();
        assertNotEquals(testCard.get(0).getRank(),game.cards.get(0).getRank());
    }
}
