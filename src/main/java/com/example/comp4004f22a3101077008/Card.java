package com.example.comp4004f22a3101077008;

public class Card {
    private String suit;
    private String rank;
    public Card (String s, String r){
        this.suit = s;
        this.rank = r;
    }
    public String getSuit(){
        return this.suit;
    }
}
