package com.assignment.snl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.assignment.snl.model.Player;
import com.assignment.snl.util.GameConstants;

/**
 * Does the job of taking the input and calling the game class for play
 * @author srathod
 *
 */
public class GameController {

	private static Logger log = Logger.getLogger(GameController.class.getName());

	private SnakesNLadderGame game = null;

	public GameController(SnakesNLadderGame game) {
		this.game = game;
	}

	public void setUp(Scanner in) throws Exception {

		System.out.print("\nEnter Number of Dice :");
		int max = 0;
		try {
			in.hasNextInt();// checks and waits till input entry
			int dice = in.nextInt();
			max = 6 * dice;

		} catch (java.util.InputMismatchException e) {
			throw new Exception("Please enter valid number of dice(Valid range is 1 to "
							+ GameConstants.MAXNOSOFDICE + ").");
		}
		if (max <= 0 || max > GameConstants.MAXNOSOFDICE) {
			new Exception("Please enter valid number of dice(Valid range is 1 to "
							+ GameConstants.MAXNOSOFDICE + ").");
		}
		game.setMaxNumberOnDie(max);

		System.out.print("\nEnter Number of Players :");
		int noOfPlayers = 0;
		try {
			in.hasNextInt();// checks and waits till input entry
			noOfPlayers = in.nextInt();
		} catch (java.util.InputMismatchException e) {
			throw new Exception("Please enter valid number of players(Valid range is 1 to "
							+ GameConstants.MAXNOSOFPLAYERS + ").");
		}
		if (noOfPlayers <= 0 || noOfPlayers > GameConstants.MAXNOSOFPLAYERS) {
			new Exception("Please enter valid number of players(Valid range is 1 to "
							+ GameConstants.MAXNOSOFPLAYERS + ").");
		}
		game.setNoOfPlayers(noOfPlayers);

		List<Player> players = new ArrayList<Player>();

		// Reads a single line from the console and stores into player
		for (int i = 0; i < noOfPlayers;) {

			System.out.print("\nEnter Name of the Player " + (i + 1) + ": ");
			if (in.hasNext()) {// blocks the call till user enters
				String pName = in.next();
				Player player = new Player(pName);
				players.add(player);
				i++;
			}
		}
		game.setPlayers(players);

		System.out.println("Initialization Done! \nNow, lets Start the game!\n");

	}

	public String start(Scanner in, boolean waitForUserInput) {
		String winnerName = null;

		if (in == null) {
			System.out.println("Game Error: Console not found.");
			return null;
		}

		try {
			game.setWaitForUserToEnter(waitForUserInput);

			// init input
			setUp(in);

			// play the game and return winner name
			winnerName = game.play(in);

			System.out.println("And the Winner of this game is " + winnerName);

		} catch (NumberFormatException | IOException e) {

			log.log(Level.SEVERE, "Invalid Input", e);
			System.out.println("Game Failure!");
		} catch (Exception e) {

			log.log(Level.SEVERE, "Some Internal Error", e);
			System.out.println("Game Failure! Cause of failure:  "
					+ e.getMessage());

		}
		return winnerName;
	}
}
