package com.example.comp4004f22a3101077008;
/*
 * This is the ConnectionMessage class. It is responsible for creating a ConnectionMessage object.
 * It has one attribute: name.
 * It has two constructors: one with no parameters and one with one parameter.
 * It has two methods: getMessage() and setName().
 * The name attribute is used to store the name of the player.
 * The getMessage() method is used to return the name of the player.
 * The setName() method is used to set the name of the player.
 * The ConnectionMessage object is used to send a message to the client to when initial startup occurs.
 */
public class ConnectionMessage {
    private String name;

    public ConnectionMessage() {
    }

    public ConnectionMessage(String name) {
        this.name = name;
    }

    public String getMessage() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
