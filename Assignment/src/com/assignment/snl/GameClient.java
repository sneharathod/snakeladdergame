package com.assignment.snl;

import java.util.Scanner;

import com.assignment.snl.controller.GameController;
import com.assignment.snl.controller.SnakesNLadderGame;

/**
 * Console game of Snakes and Ladder
 * @author srathod
 *
 */
public class GameClient {

	public static void main(String[] args) {
		Scanner in = null;
		GameController cntrl = new GameController(new SnakesNLadderGame());
		try {
			in = new Scanner(System.in);// read from console

			cntrl.start(in, true);
		} finally {
			in.close();
		}
	}

}
