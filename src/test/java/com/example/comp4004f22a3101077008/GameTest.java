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
    @Test
    void testDrawCard(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.players.add(p);
        }
        assertFalse(game.drawCard(1));
        game.populateDeck();
        game.shuffleDeck();
        assertTrue(game.drawCard(1));
        assertEquals(1,game.players.get(0).handSize());
    }
    @Test
    void testStartGame(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.players.add(p);
        }
        assertNull(game.topCard);
        game.startGame();
        assertEquals(31,game.cards.size());
        for(Player p :game.players){
            assertEquals(5,p.handSize());
        }
        assertNotEquals("8",game.topCard.getRank());
        assertNotNull(game.topCard);
    }

    @Test
    void testCheckQueen(){
        assertTrue(game.checkQueen("Q"));
    }
    @Test
    void testCheckAces(){
        assertTrue(game.checkAces("A"));
    }
    @Test
    void testCheck8(){
        assertTrue(game.checkAces("8"));
    }
    @Test
    void testCheck2(){
        assertTrue(game.check2("2"));
    }

    @Test
    void testPlayCard(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.players.add(p);
        }
        game.startGame();
        game.topCard = new Card("S","Q");
        Card c = new Card("D","Q");
        game.players.get(0).cards.set(0,c);
        String res = game.playCard(1,"QD");
        assertEquals("Q Played",res);

        game.topCard = new Card("S","2");
        c = new Card("D","2");
        game.players.get(0).cards.set(0,c);
        res = game.playCard(1,"2D");
        assertEquals("2 Played",res);

        game.topCard = new Card("S","A");
        c = new Card("D","A");
        game.players.get(0).cards.set(0,c);
        res = game.playCard(1,"AD");
        assertEquals("A Played",res);

        game.topCard = new Card("S","2");
        c = new Card("S","8");
        game.players.get(0).cards.set(0,c);
        res = game.playCard(1,"8S");
        assertEquals("8 Played",res);

        game.topCard = new Card("S","2");
        c = new Card("S","8");
        game.players.get(0).cards.set(0,c);
        res = game.playCard(1,"4S");
        assertEquals("Played",res);

        game.topCard = new Card("S","2");
        c = new Card("S","8");
        game.players.get(0).cards.set(0,c);
        res = game.playCard(1,"4D");
        assertEquals("not played",res);
    }
}
