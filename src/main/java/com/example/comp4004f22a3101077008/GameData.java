package com.example.comp4004f22a3101077008;

import java.util.ArrayList;

public interface GameData {
    public Card getTopCard();
    public void setTopCard(Card c);
    public ArrayList<Player> getPlayers();
    public void setPlayers(ArrayList<Player> ps);
    public ArrayList<Card> getCards();
    public void setCards(ArrayList<Card> cs);
    public void addPlayer(Player p);
    public String getDirection();
    public void setDirection(String dir);
    public void setCurrentPlayer(int id);
    public int getCurrentPlayer();
}
