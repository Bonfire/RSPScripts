package com.bonfire;

public class Room {
    private final int position;
    private final char symbol;
    private Room next;
    private Room previous;

    Room(int position, char symbol)
    {
        this.position = position;
        this.symbol = symbol;
    }

    public int getPosition() {
        return position;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setNext(Room room) {
        next = room;
    }

    public void setPrevious(Room lastRoom) {
        previous = lastRoom;
    }
}
