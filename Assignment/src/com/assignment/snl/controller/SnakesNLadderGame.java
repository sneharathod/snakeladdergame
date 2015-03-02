package com.assignment.snl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.assignment.snl.model.Board;
import com.assignment.snl.model.Player;
import com.assignment.snl.util.GameConstants;

/**
 * Contains complete logic of game
 * Rolling of Dice
 * Move of Player
 * Steps of game
 * Status message of game
 * @author srathod
 *
 */
public class SnakesNLadderGame {

	private static Logger log = Logger.getLogger(SnakesNLadderGame.class.getName());

	private List<Player> players = new ArrayList<Player>();

	private Board board = null;

	private int noOfPlayers = 0;// default

	private boolean waitForUserToEnter = false;

	private int maxNumberOnDie = 6;

	public SnakesNLadderGame() {

		try {
			board = new Board();
		} catch (Exception e) {
			log.log(Level.SEVERE, "", e);
		}
	}

	public void displayGameStatus() {
		List<Player> players = this.getPlayers();

		System.out.println(GameConstants.separater);
		System.out.println("Status of Players:");

		for (Player player : players) {
			System.out.println("Player " + player.getName()
					+ " Current Position :" + player.getMarker());
		}

		System.out.println(GameConstants.separater);
	}
	
	public void displayMovingStatus(String move, String playerName, Integer mark) {
		System.out.println(move + "Moving Player " + playerName
				+ " to new position " + mark);
	}

	/**
	 * Moves player on board
	 * @param playerName
	 * @param mark
	 * @param diceDigit
	 * @return
	 */
	public int movePlayer(String playerName, Integer mark, int diceDigit) {
		System.out.println("Player " + playerName + " gets " + diceDigit
				+ " on Dice!");

		Integer prevMarker = mark;
		mark = mark + diceDigit;

		displayMovingStatus("", playerName, mark);

		while (prevMarker != mark) {
			// iterate till mark changes
			prevMarker = mark;

			// check Snake or Ladder
			if (this.board.getSnakesLadders().containsKey(mark)) {

				Integer toe = this.board.getSnakesLadders().get(mark);
				String message = "";
				if (mark > toe) {
					// incase of snake tip is greater than toe
					message = "Snake bite: ";
				} else {
					// toe is greater than ladder
					message = "Ladder found: ";
				}
				mark = toe;

				displayMovingStatus(message, playerName, mark);
			}

			if (mark >= this.board.getTotalBoardItems()) {
				// winner found
				mark = this.board.getTotalBoardItems();
				break;
			}
			
		}

		return mark;
	}

	/**
	 * Find dice digit for a player
	 * @return
	 */
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

	/**
	 * Play game till one of the player is winner
	 * @param in
	 * @return
	 */
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
					
					//roll the dice
					int diceDigit = rollTheDice();
					
					//move the player as per dice value
					int mark = this.players.get(i).getMarker();
					
					int newMark = movePlayer(playerName,mark, diceDigit);
					
					//set the marker
					this.players.get(i).setMarker(newMark);
					
					// show status of game
					displayGameStatus();

					//check if winner
					if (newMark >= this.board.getTotalBoardItems()) {
						done = true;
						winnerName = playerName;
						break;
					}
				}
			}
		}
		return winnerName;
	}

	public void setMaxNumberOnDie(int maxNumberOnDie) {
		this.maxNumberOnDie = maxNumberOnDie;
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
}
