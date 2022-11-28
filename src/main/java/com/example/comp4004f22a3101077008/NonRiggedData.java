package com.example.comp4004f22a3101077008;

import java.util.ArrayList;

public class NonRiggedData implements GameData{
    ArrayList<Player> players;
    ArrayList<Card> cards;
    Card topCard;

    public NonRiggedData(){
        players = new ArrayList<>();
        cards = new ArrayList<>();
        topCard = new Card();
    }

    @Override
    public Card getTopCard() {
        return this.topCard;
    }

    @Override
    public void setTopCard(Card c) {
        this.topCard = c;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(ArrayList<Player> ps) {
        this.players = ps;
    }

    @Override
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    @Override
    public void setCards(ArrayList<Card> cs) {
        this.cards = cs;
    }

    @Override
    public void addPlayer(Player p) {
        this.players.add(p);
    }
}
