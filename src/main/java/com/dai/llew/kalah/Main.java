package com.dai.llew.kalah;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();
        board.displayBoard();


        int pitId = 1;
        int stones = board.getPit(1).takeStones();

        while (stones > 0) {
            pitId++;
            board.getPit(pitId).addStone();
            stones--;
        }

        board.displayBoard();
    }
}
