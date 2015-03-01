package com.assignment.snl.test;

import java.util.Scanner;

import junit.framework.TestCase;

import org.junit.Test;

import com.assignment.snl.GameClient;
import com.assignment.snl.controller.SnakesNLadderGame;

public class SnakeNLadderGameTest extends TestCase {

	private static final String propFileName = "gameInput.Properties";

	// assigning the values
	protected void setUp() {
		// do some init
	}

	@Test
	public void test() {
		System.out.println("Inside test() for JUnit testing!");

		SnakesNLadderGame game = new SnakesNLadderGame();
		Scanner in = null;
		try {
			in = new Scanner(SnakeNLadderGameTest.class.getResourceAsStream(propFileName));
			
			game = new SnakesNLadderGame();
			
			String winnerName = game.start(in, false);

			assertEquals(winnerName != null || !winnerName.isEmpty(), true);

		} catch (NumberFormatException e) {

			e.printStackTrace();
			System.out.println("Game Failure!");
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Game Failure due to invalid Input!");

		} finally {
			in.close();
		}

	}

}
