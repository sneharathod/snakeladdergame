package com.assignment.snl.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.assignment.snl.util.GameConstants;
import com.assignment.snl.util.GamePropertyReader;

public class SnakesNLadderGame {

	private List<Player> players = new ArrayList<Player>();

	private Board board;

	private int noOfPlayers = 0;// default

	private GamePropertyReader reader;

	private static String separater = "------------------------------------------------------------------------------------";

	private boolean waitForUserToEnter = false;

	private int maxNumberOnDie = 6;

	public SnakesNLadderGame() {

		try {
			board = new Board();

			// read number of dice from properties file
			reader = new GamePropertyReader(GameConstants.GAMEPROPERTYFILE);

			this.maxNumberOnDie = 6 * Integer.parseInt(reader
					.getProperty(GameConstants.NUMBEROFDICE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize(Scanner in) throws Exception {

		System.out.print("\nEnter Number of Players :");
		int noOfPlayers = 0;
		try {
			in.hasNextInt();
			noOfPlayers = in.nextInt();

			this.setNoOfPlayers(noOfPlayers);

		} catch (java.util.InputMismatchException e) {
			System.out.println("\nPlease enter valid number of players.");
			// throw new Exception("Invalid Input!");
			return;
		}

		if (noOfPlayers > 0) {
			List<Player> players = new ArrayList<Player>();

			// Reads a single line from the console and stores into player
			for (int i = 0; i < noOfPlayers;) {

				System.out
						.print("\nEnter Name of the Player " + (i + 1) + ": ");
				if (in.hasNext()) {// blocks the call till user enters
					String pName = in.next();
					Player player = new Player(pName);
					players.add(player);
					i++;
				}
			}
			this.setPlayers(players);

			System.out
					.println("Initialization Done! Now, lets Start the game!\n");
		}

	}

	public void displayGameStatus() {
		List<Player> players = this.getPlayers();

		System.out.println(separater);
		System.out.println("Status of Players:");

		for (Player player : players) {
			System.out.println("Player " + player.getName()
					+ " Current Position :" + player.getMarker());
		}
		System.out.println(separater);
	}

	public int movePlayer(int index, int diceDigit) {
		Player player = this.getPlayers().get(index);

		System.out.println("Player " + player.getName() + " gets " + diceDigit
				+ " on Dice!");

		int mark = player.getMarker();
		int prevMarker = mark;
		mark = mark + diceDigit;
		System.out.println("Moving Player " + player.getName()
				+ " to new position " + mark);

		while (prevMarker != mark) {// iterate till mark changes
			prevMarker = mark;
			if (mark >= this.board.getTotalBoardItems()) {
				// winner found
				break;
			}
			// check Snake
			if (this.board.getSnakes().containsKey(mark + "")) {
				int toe = Integer.parseInt(this.board.getSnakes()
						.get(mark + ""));
				mark = toe;
				System.out.println("Snake Bite: Moving Player "
						+ player.getName() + " to new position " + mark);
			}

			// check Ladder at old or new position
			if (this.board.getLadders().containsKey(mark + "")) {
				int toe = Integer.parseInt(this.board.getLadders().get(
						mark + ""));
				mark = toe;
				System.out.println("Found Ladder: Moving Player "
						+ player.getName() + " to new position " + mark);
			}

			if (mark >= this.board.getTotalBoardItems()) {
				// winner found
				break;
			}

		}
		this.getPlayers().get(index).setMarker(mark);
		return mark;
	}

	public int rollTheDice() {
		Random generator = new Random();
		int sum = 0;
		// Random generates entries from 0 to max-1 specified
		int i = 0;
		do {
			// second chance
			if (i != 0) {
				System.out.println("Nice, you get second chance with Dice!");
			}
			i = generator.nextInt(maxNumberOnDie) + 1;
			System.out.println("Dice face value: " + i);

			sum += i;
		} while (i == maxNumberOnDie);

		return sum;
	}

	public String play(Scanner in) {
		String winnerName = "";

		boolean done = false;
		if (this.noOfPlayers > 0) {

			while (!done) {
				// played till one of the player wins
				for (int i = 0; i < this.noOfPlayers; i++) {
					String playerName = this.players.get(i).getName();

					if (waitForUserToEnter) {
						System.out.println("\nPlayer " + playerName
								+ " Press enter to Roll the Dice!");
						String str = in.nextLine();// read enter
					}
					int diceDigit = rollTheDice();
					int mark = movePlayer(i, diceDigit);

					// show status of game
					displayGameStatus();

					if (mark >= this.board.getTotalBoardItems()) {
						done = true;
						winnerName = playerName;
						break;
					}
				}
			}
		}
		return winnerName;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}

	public boolean isWaitForUserToEnter() {
		return waitForUserToEnter;
	}

	public void setWaitForUserToEnter(boolean waitForUserToEnter) {
		this.waitForUserToEnter = waitForUserToEnter;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getMaxNumberOnDie() {
		return maxNumberOnDie;
	}

	public void setMaxNumberOnDie(int maxNumberOnDie) {
		this.maxNumberOnDie = maxNumberOnDie;
	}

	public String start(Scanner in, boolean waitForUserInput) {
		String winnerName = null;
		try {
			this.setWaitForUserToEnter(waitForUserInput);
			this.initialize(in); // init

			if (this.getNoOfPlayers() < 1) {
				return null;
			}

			winnerName = this.play(in);

			System.out.println("And the Winner of this game is " + winnerName);

		} catch (NumberFormatException | IOException e) {

			e.printStackTrace();
			System.out.println("Game Failure!");
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Game Failure due to invalid Input!");

		}
		return winnerName;
	}
}
