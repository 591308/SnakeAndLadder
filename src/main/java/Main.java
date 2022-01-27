package main.java;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException{

        Scanner scanner = new Scanner(System.in);

        List<Snake> snakes = new ArrayList<>();
        snakes.add(new Snake(16,6));
        snakes.add(new Snake(49,11));
        snakes.add(new Snake(47,26));
        snakes.add(new Snake(87,24));
        snakes.add(new Snake(56,53));
        snakes.add(new Snake(62,18));
        snakes.add(new Snake(64,60));
        snakes.add(new Snake(98,18));
        snakes.add(new Snake(93,73));
        snakes.add(new Snake(95,75));



        List<Ladder> ladders = new ArrayList<>();
        ladders.add(new Ladder(2, 38));
        ladders.add(new Ladder(4, 14));
        ladders.add(new Ladder(8, 31));
        ladders.add(new Ladder(21, 42));
        ladders.add(new Ladder(51, 67));
        ladders.add(new Ladder(28, 84));
        ladders.add(new Ladder(71, 91));
        ladders.add(new Ladder(80, 100));
        ladders.add(new Ladder(36, 44));


        System.out.println("Type in number of players between 2 and 4");
        int noOfPlayers = scanner.nextInt();
        List<Player> players = new ArrayList<>();
        for(int i =0; i < noOfPlayers; i++){
            System.out.println("Give player " + (i+1) + " name");
            players.add(new Player(scanner.next()));
        }

        Game game = new Game();

        game.setPlayers(players);
        game.setLadder(ladders);
        game.setSnakes(snakes);

        System.out.println("Game Starts");
        game.startGame();
    }
}
