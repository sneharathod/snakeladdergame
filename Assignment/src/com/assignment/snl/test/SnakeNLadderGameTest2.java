package com.assignment.snl.test;

import java.util.Scanner;

import junit.framework.TestCase;

import org.junit.Test;

import com.assignment.snl.controller.SnakesNLadderGame;

public class SnakeNLadderGameTest2 extends TestCase {

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
			in = new Scanner(
					SnakeNLadderGameTest.class
							.getResourceAsStream(propFileName));

			game = new SnakesNLadderGame();

			System.out.println("Testing roll the Dice!");

			//test dice rolling
			int maxNoOnDice = game.getMaxNumberOnDie();
			assertTrue(maxNoOnDice!=0);
			
			int dicefacevalue = game.rollTheDice();
			//diceFaceValue in maxNoDice range
			assertTrue(maxNoOnDice >= dicefacevalue && dicefacevalue > 0);
			
			System.out.println("Game's roll the Dice is working fine!");

			String winnerName = game.start(in, false);
			assertEquals(winnerName != null && !winnerName.isEmpty(), true);
			System.out.println("Game is working fine");

		} catch (NumberFormatException e) {

			//e.printStackTrace();
			System.out.println("Game Failure!");
		} catch (Exception e) {

			//e.printStackTrace();
			System.out.println("Game Failure due to invalid Input!");

		} finally {
			in.close();
		}

	}

}
