package com.example.comp4004f22a3101077008;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
@Component
public class NonRiggedData implements GameData{
    ArrayList<Player> players;
    ArrayList<Card> cards;
    Card topCard;

    String direction;

    int nextRound;
    int currentPlayer;
    public NonRiggedData(){
        players = new ArrayList<>();
        cards = new ArrayList<>();
        topCard = new Card();
        this.direction = "left";
        this.currentPlayer = 1;
        nextRound = 1;
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

    @Override
    public String getDirection() {
        return this.direction;
    }

    @Override
    public void setDirection(String dir) {
        this.direction = dir;
    }
    public void setCurrentPlayer(int id) {
        this.currentPlayer = id;
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }
    public int getNextRound(){return this.nextRound;}
    public void setNextRound(int r){this.nextRound=r;}

}
