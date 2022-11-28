package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    GameLogic game = new GameLogic();
    GameData gd = new NonRiggedData();
    @Test
    void testPopulateDeck(){
        game.populateDeck(gd.getCards());
        assertEquals(52,gd.getCards().size());
    }
    @Test
    void testShuffleDeck(){
        game.populateDeck(gd.getCards());
        String r = gd.getCards().get(0).getRank();
        game.shuffleDeck(gd.getCards());
        assertNotEquals(r,gd.getCards().get(0).getRank());
    }
    @Test
    void testDealCards(){
        ArrayList<Player> ps = new ArrayList<>();
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            ps.add(p);
        }
        gd.setPlayers(ps);
        assertFalse(game.dealCards(gd.getCards(),gd.getPlayers()));
        game.populateDeck(gd.getCards());
        game.shuffleDeck(gd.getCards());
        assertTrue(game.dealCards(gd.getCards(),gd.getPlayers()));
        for(Player p :gd.getPlayers()){
            assertEquals(5,p.handSize());
        }
    }
    @Test
    void testDrawCard(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            gd.getPlayers().add(p);
        }
        assertFalse(game.drawCard(gd.getCards(),gd.getPlayers().get(0)));
        game.populateDeck(gd.getCards());
        game.shuffleDeck(gd.getCards());
        assertTrue(game.drawCard(gd.getCards(),gd.getPlayers().get(0)));
        assertEquals(1,gd.getPlayers().get(0).handSize());
    }
    @Test
    void testStartGame(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            gd.addPlayer(p);
        }
        assertEquals("",gd.getTopCard().getRank());
        assertEquals("",gd.getTopCard().getSuit());
        game.startGame(gd.getTopCard(),gd.getCards(),gd.getPlayers());
        assertEquals(31,gd.getCards().size());
        for(Player p :gd.getPlayers()){
            assertEquals(5,p.handSize());
        }
        assertNotEquals("8",gd.getTopCard().getRank());
        assertNotNull(gd.getTopCard());
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
            gd.getPlayers().add(p);
        }
        game.startGame(gd.getTopCard(),gd.getCards(),gd.getPlayers());
        Card tc = new Card("S","Q");
        gd.setTopCard(tc);
        Card c = new Card("D","Q");
        gd.getPlayers().get(0).cards.set(0,c);
        String res = game.playCard(gd.getPlayers().get(0), "QD",gd.getTopCard());
        assertEquals("Q Played",res);

        tc = new Card("S","2");
        gd.setTopCard(tc);
        c = new Card("D","2");
        gd.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(gd.getPlayers().get(0), "2D",gd.getTopCard());
        assertEquals("2 Played",res);

        tc = new  Card("S","A");
        gd.setTopCard(tc);
        c = new Card("D","A");
        gd.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(gd.getPlayers().get(0), "AD",gd.getTopCard());
        assertEquals("A Played",res);

        tc = new Card("S","2");
        gd.setTopCard(tc);
        c = new Card("S","8");
        gd.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(gd.getPlayers().get(0), "8S",gd.getTopCard());
        assertEquals("8 Played",res);

        tc = new Card("S","2");
        gd.setTopCard(tc);
        c = new Card("S","8");
        gd.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(gd.getPlayers().get(0), "4S",gd.getTopCard());
        assertEquals("Played",res);

        tc = new Card("S","2");
        gd.setTopCard(tc);
        c = new Card("S","8");
        gd.getPlayers().get(0).cards.set(0,c);
        res = game.playCard(gd.getPlayers().get(0), "4D",gd.getTopCard());
        assertEquals("not played",res);
    }

    @Test
    void testCalculateScore(){
        for(int i=0;i<4;i++){
            Player p = new Player(i+1);
            gd.getPlayers().add(p);
        }
        Card c1 = new Card("D","Q");
        Card c2 = new Card("D","5");
        Card c3 = new Card("D","8");
        Card c4 = new Card("D","10");
        Card c5 = new Card("D","2");

        for(int i=1;i<gd.getPlayers().size();i++){
            gd.getPlayers().get(i).addCard(c1);
            gd.getPlayers().get(i).addCard(c2);
            gd.getPlayers().get(i).addCard(c3);
            gd.getPlayers().get(i).addCard(c4);
            gd.getPlayers().get(i).addCard(c5);
        }
        game.calculateScore(gd.getPlayers());
        assertEquals(0,gd.getPlayers().get(0).getScore());
        for(int i=1;i<gd.getPlayers().size();i++){
            assertEquals(77,gd.getPlayers().get(i).getScore());
        }
    }
}
