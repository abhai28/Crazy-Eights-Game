package com.example.comp4004f22a3101077008;

import java.util.ArrayList;

public class Player {
    private final int playerID;
    private ArrayList<Card> cards;
    private int score;
    public Player(int id){
        this.playerID = id;
        this.cards = new ArrayList<>();
        this.score = 0;
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
    public void setScore(int num){
        this.score = num;
    }
    public int getScore(){
        return this.score;
    }
}
