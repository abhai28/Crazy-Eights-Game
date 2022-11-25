package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    Game game = new Game();
    @Test
    void testPopulateDeck(){
        game.populateDeck();
        assertEquals(52,game.cards.size());
    }
}
