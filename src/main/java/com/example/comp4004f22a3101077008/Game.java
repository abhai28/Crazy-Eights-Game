package com.example.comp4004f22a3101077008;

import java.util.ArrayList;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();

    public void populateDeck() {
        String [] suit = {"S","C","D","H"};
        String [] rank = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for (String s : suit) {
            for (String value : rank) {
                Card c = new Card(s, value);
                cards.add(c);
            }
        }
    }
}
