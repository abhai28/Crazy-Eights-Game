package com.example.comp4004f22a3101077008;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
/*
 * NonRiggedData class
 * This class is used to handle the game data
 * It is used to set and get the top card, players, cards, direction, current player, next round, number of twos played, number of turns, and total number of twos played.
 * It is set as a component so that it can be autowired in other classes. This is done to avoid creating multiple instances of the class.
 * The @Component annotation is used to indicate that the class is a component. It is used to auto-detect and auto-configure beans using classpath scanning.
 */
@Component
public class NonRiggedData implements GameData{
    ArrayList<Player> players;
    ArrayList<Card> cards;
    Card topCard;

    String direction;

    int nextRound;
    int currentPlayer;

    int numTwoPlayed;
    int numTurns;
    int totalTwoPlayed;
    public NonRiggedData(){
        players = new ArrayList<>();
        cards = new ArrayList<>();
        topCard = new Card();
        this.direction = "left";
        this.currentPlayer = 1;
        nextRound = 1;
        numTwoPlayed = 0;
        numTurns = 0;
        totalTwoPlayed = 0;
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

    @Override
    public int getNumTwoPlayed() {
        return numTwoPlayed;
    }

    @Override
    public void setNumTwoPlayed(int t) {
        numTwoPlayed = t;
    }

    @Override
    public int getNumTurns() {
        return this.numTurns;
    }

    @Override
    public void setNumTurns(int num) {
        this.numTurns = num;
    }

    @Override
    public void setTotalTwoPlayed(int num) {
        this.totalTwoPlayed=num;
    }

    @Override
    public int getTotalTwoPlayed() {
        return this.totalTwoPlayed;
    }


}
