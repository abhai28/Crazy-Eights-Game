package com.example.comp4004f22a3101077008;

import java.util.ArrayList;
import java.util.Collections;

public interface Game {

    public void startGame();
    public void populateDeck();
    public void shuffleDeck();
    public boolean drawCard(int id);
    public boolean checkQueen(String r);
    public boolean check8(String r);
    public boolean checkAces(String r);
    public boolean check2(String r);
    public String playCard(int id, String c);
    public void calculateScore();
    public boolean dealCards();
    public Card getTopCard();
    public void setTopCard(Card c);
    public ArrayList<Player> getPlayers();
    public void setPlayers(ArrayList<Player> ps);
    public ArrayList<Card> getCards();
    public void setCards(ArrayList<Card> cs);
    public void addPlayer(Player p);
}
