package com.assignment.snl;

import java.io.IOException;
import java.util.Scanner;

import com.assignment.snl.model.SnakesNLadderGame;

public class GameClient {

	public static void main(String[] args) {
		Scanner in = null;
		try {
			in = new Scanner(System.in);// read from console

			gameOn(in, true);
		} finally {
			in.close();
		}
	}

	public static String gameOn(Scanner in, boolean waitForUserInput) {
		SnakesNLadderGame game = null;
		String winnerName = null;
		try {

			game = new SnakesNLadderGame();
			game.setWaitForUserToEnter(waitForUserInput);
			game.initialize(in); // init

			if (game.getNoOfPlayers() < 1) {
				return null;
			}

			winnerName = game.play(in);

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
