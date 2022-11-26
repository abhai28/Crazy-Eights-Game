package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game = new NormalGame();
    @Test
    void testPopulateDeck(){
        game.populateDeck();
        assertEquals(52,game.getCards().size());
    }
    @Test
    void testShuffleDeck(){
        game.populateDeck();
        String r = game.getCards().get(0).getRank();
        game.shuffleDeck();
        assertNotEquals(r,game.getCards().get(0).getRank());
    }
    @Test
    void testDealCards(){
        ArrayList<Player> ps = new ArrayList<>();
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            ps.add(p);
        }
        game.setPlayers(ps);
        assertFalse(game.dealCards());
        game.populateDeck();
        game.shuffleDeck();
        assertTrue(game.dealCards());
        for(Player p :game.getPlayers()){
            assertEquals(5,p.handSize());
        }
    }
    @Test
    void testDrawCard(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.getPlayers().add(p);
        }
        assertFalse(game.drawCard(1));
        game.populateDeck();
        game.shuffleDeck();
        assertTrue(game.drawCard(1));
        assertEquals(1,game.getPlayers().get(0).handSize());
    }
    @Test
    void testStartGame(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.getPlayers().add(p);
        }
        assertNull(game.getTopCard());
        game.startGame();
        assertEquals(31,game.getCards().size());
        for(Player p :game.getPlayers()){
            assertEquals(5,p.handSize());
        }
        assertNotEquals("8",game.getTopCard().getRank());
        assertNotNull(game.getTopCard());
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
        assertTrue(game.check8("8"));
    }
    @Test
    void testCheck2(){
        assertTrue(game.check2("2"));
    }

    @Test
    void testPlayCard(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.getPlayers().add(p);
        }
        game.startGame();
        Card tc = new Card("S","Q");
        game.setTopCard(tc);
        Card c = new Card("D","Q");
        game.getPlayers().get(0).cards.set(0,c);
        String res = game.playCard(1,"QD");
        assertEquals("Q Played",res);

        tc = new Card("S","2");
        game.setTopCard(tc);
        c = new Card("D","2");
        game.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(1,"2D");
        assertEquals("2 Played",res);

        tc = new  Card("S","A");
        game.setTopCard(tc);
        c = new Card("D","A");
        game.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(1,"AD");
        assertEquals("A Played",res);

        tc = new Card("S","2");
        game.setTopCard(tc);
        c = new Card("S","8");
        game.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(1,"8S");
        assertEquals("8 Played",res);

        tc = new Card("S","2");
        game.setTopCard(tc);
        c = new Card("S","8");
        game.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(1,"4S");
        assertEquals("Played",res);

        tc = new Card("S","2");
        game.setTopCard(tc);
        c = new Card("S","8");
        game.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(1,"4D");
        assertEquals("not played",res);
    }

    @Test
    void testCalculateScore(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            game.getPlayers().add(p);
        }
        Card c1 = new Card("D","Q");
        Card c2 = new Card("D","5");
        Card c3 = new Card("D","8");
        Card c4 = new Card("D","10");
        Card c5 = new Card("D","2");

        for(int i=1;i<game.getPlayers().size();i++){
            game.getPlayers().get(i).addCard(c1);
            game.getPlayers().get(i).addCard(c2);
            game.getPlayers().get(i).addCard(c3);
            game.getPlayers().get(i).addCard(c4);
            game.getPlayers().get(i).addCard(c5);
        }
        game.calculateScore();
        assertEquals(0,game.getPlayers().get(0).getScore());
        for(int i=1;i<game.getPlayers().size();i++){
            assertEquals(77,game.getPlayers().get(i).getScore());
        }
    }
}
