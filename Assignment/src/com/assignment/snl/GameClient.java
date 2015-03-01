package com.assignment.snl;

import java.util.Scanner;

import com.assignment.snl.model.SnakesNLadderGame;

public class GameClient {

	public static void main(String[] args) {
		Scanner in = null;
		SnakesNLadderGame game = new SnakesNLadderGame();
		try {
			in = new Scanner(System.in);// read from console

			game = new SnakesNLadderGame();
			game.start(in, true);

		} finally {
			in.close();
		}
	}

}
