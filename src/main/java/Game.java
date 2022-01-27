package main.java;

import java.util.*;

public class Game {

    private final Board board;
    private int initialNrOfPlayers;
    private Queue<Player> players;
    public static final int DEFAULT_BOARD_SIZE = 100;
    private int counter = 0;

    public Game(int size) {
        this.board = new Board(size);
        this.players = new LinkedList<Player>();
    }

    public Game() {
        this(Game.DEFAULT_BOARD_SIZE);
    }

    private int valueOfDice() {
        return Dice.roll();
    }

    public void setPlayers(List<Player> players) {
        this.players = new LinkedList<>();
        this.initialNrOfPlayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<String, Integer>();
        for (Player player : players) {
            this.players.add(player);
            playerPieces.put(player.getId(), 1);
        }
        board.setPlayers(playerPieces);
    }

    public void setLadder(List<Ladder> ladders) {
        board.setLadders(ladders);
    }

    public void setSnakes(List<Snake> snakes) {
        board.setSnakes(snakes);
    }

    private boolean hasPlayerWon(Player player) {
        int playerPos = board.getPlayers().get(player.getId());
        int playerWinnPos = board.getSize();
        return playerPos == playerWinnPos;
    }

    private boolean isGameCompleted() {
        int currentPlayerCounter = players.size();
        return currentPlayerCounter < initialNrOfPlayers;
    }

    private void movePlayer(Player player, int roll) {

        int oldPos = board.getPlayers().get(player.getId());
        int newPos = oldPos + roll;

        int boardsize = board.getSize();

        if(newPos <= boardsize) {

            if(roll == 6 && player.isFanget() == true) {
                System.out.println(player.getName() + " rolled " + roll + " you can start playing again");
                board.getPlayers().put(player.getId(), 1);
                player.setFanget(false);
            }

            if(roll == 6 && player.isFanget() == false && counter < 3) {
                counter++;
                System.out.println(player.getName() + " rolled " + roll + " roll again! Roll in a row [" + counter + "]");
                movePlayer(player, valueOfDice());
            }
            if(roll == 6 && player.isFanget() == false && counter == 3) {
                System.out.println(player.getName() + " rolled " + roll + ", " + counter + " times in a row, go to pos 1");
                board.getPlayers().put(player.getId(), 1);
                player.setFanget(true);
            }
            // if player roll 6 and is not locked
            counter = 0;

            if (roll != 6 && player.isFanget() == true) {
                System.out.println(player.getName() + " rolled " + roll + " need to roll 6 to start playing again");
                board.getPlayers().put(player.getId(), 1);
            }
            if (roll != 6 && player.isFanget() == false) {

                board.getPlayers().put(player.getId(), newPos);
                System.out.println(player.getName() + " rolled " + roll + " and moved from [" + oldPos + "] to [" + newPos + "]");

                for (Ladder ladders : board.getLadders()) {
                    for (Snake snakes : board.getSnakes()) {
                        if (ladders.getStart() == newPos) {
                            newPos = ladders.getEnd();
                            System.out.println(player.getName() + " stepped on ladder and moved from [" + ladders.getStart() + "] to [" + ladders.getEnd() + "]");
                            board.getPlayers().put(player.getId(), newPos);
                        } else if (snakes.getStart() == newPos) {
                            newPos = snakes.getEnd();
                            System.out.println(player.getName() + " stepped on snake and moved from [" + snakes.getStart() + "] to [" + snakes.getEnd() + "]");
                            board.getPlayers().put(player.getId(), newPos);
                        }
                    }
                } // end for ladders and snakes
            } // end of roll!=6
        } else{
            System.out.println(player.getName() + " rolled bigger number than board size, player stays in place");
        }
    }
    public void startGame() throws InterruptedException {
        while (!isGameCompleted()) {
            int diceVal = valueOfDice();
            Player currentPlayer = players.poll();
            movePlayer(currentPlayer, diceVal);
            Thread.sleep(100);
            if (hasPlayerWon(currentPlayer)) {
                System.out.println(currentPlayer.getName() + " has won!");
                board.getPlayers().remove(currentPlayer.getId());
            } else {
                players.add(currentPlayer);
            }
        }
    }
}
