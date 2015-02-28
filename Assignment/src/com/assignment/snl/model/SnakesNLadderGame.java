package com.assignment.snl.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.assignment.snl.util.GameConstants;
import com.assignment.snl.util.GamePropertyReader;

public class SnakesNLadderGame {

	private Hashtable<String, String> snakes = new Hashtable<String, String>();

	private Hashtable<String, String> ladders = new Hashtable<String, String>();

	private int totalBoardItems = 0;

	private List<Player> players = new ArrayList<Player>();

	private int noOfPlayers = 0;// default

	private GamePropertyReader reader;

	private static String separater = "------------------------------------------------------------------------------------";

	private boolean waitForUserToEnter = false;

	public SnakesNLadderGame() throws NumberFormatException, IOException {

		reader = new GamePropertyReader(GameConstants.GAMEPROPERTYFILE);
		int boardLength;
		boardLength = Integer.parseInt(reader
				.getProperty(GameConstants.BOARDLENGTH));

		int boardWidth = Integer.parseInt(reader
				.getProperty(GameConstants.BOARDWIDTH));

		this.setTotalBoardItems(boardLength * boardWidth);

		String snakesStr = reader.getProperty(GameConstants.SNAKES);

		String ladderStr = reader.getProperty(GameConstants.LADDERS);

		this.setSnakes(createHashTable(snakesStr));
		this.setLadders(createHashTable(ladderStr));
	}

	private static Hashtable<String, String> createHashTable(String str) {
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		String[] arr = str.split(";");
		for (String item : arr) {
			item = item.replace("(", "").replace(")", "");
			String[] itemArr = item.split(",");
			/* snakes/ladders tip toe put in table */
			hashTable.put(itemArr[0], itemArr[1]);
		}

		return hashTable;
	}

	public void showGameStatus() {
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
			if (mark >= this.getTotalBoardItems()) {
				// winner found
				break;
			}
			// check Snake
			if (this.getSnakes().containsKey(mark + "")) {
				int toe = Integer.parseInt(this.getSnakes().get(mark + ""));
				mark = toe;
				System.out.println("Snake Bite: Moving Player "
						+ player.getName() + " to new position " + mark);
			}

			// check Ladder at old or new position
			if (this.getLadders().containsKey(mark + "")) {
				int toe = Integer.parseInt(this.getLadders().get(mark + ""));
				mark = toe;
				System.out.println("Found Ladder: Moving Player "
						+ player.getName() + " to new position " + mark);
			}

			if (mark >= this.getTotalBoardItems()) {
				// winner found
				break;
			}

		}
		this.getPlayers().get(index).setMarker(mark);
		return mark;
	}

	public int rollTheDice() {
		Random generator = new Random();
		int i = generator.nextInt(6) + 1;
		return i;
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
					.println("Initialization Done! Now, lets Start the game!");
		}

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
					showGameStatus();

					if (mark >= this.totalBoardItems) {
						done = true;
						winnerName = playerName;
						break;
					}
				}
			}
		}
		return winnerName;
	}

	public Hashtable<String, String> getSnakes() {
		return snakes;
	}

	public void setSnakes(Hashtable<String, String> snakes) {
		this.snakes = snakes;
	}

	public Hashtable<String, String> getLadders() {
		return ladders;
	}

	public void setLadders(Hashtable<String, String> ladders) {
		this.ladders = ladders;
	}

	public int getTotalBoardItems() {
		return totalBoardItems;
	}

	public void setTotalBoardItems(int totalBoardItems) {
		this.totalBoardItems = totalBoardItems;
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

}
