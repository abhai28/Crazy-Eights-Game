package com.example.comp4004f22a3101077008;

import java.util.ArrayList;

public class Player {
    private int playerID;
    private ArrayList<Card> cards;
    public Player(int id){
        this.playerID = id;
        this.cards = new ArrayList<>();
    }
    public int getID(){
        return this.playerID;
    }
    public void addCard(Card c){
        this.cards.add(c);
    }
    public Card getCard(int i){
        return this.cards.get(i);
    }
}
