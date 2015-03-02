package com.assignment.snl.test;

import java.util.Scanner;

import junit.framework.TestCase;

import org.junit.Test;

import com.assignment.snl.controller.GameController;
import com.assignment.snl.controller.SnakesNLadderGame;

public class SnakeNLadderGameTest extends TestCase {

	private static final String propFileName = "gameInput.Properties";

	// assigning the values
	protected void setUp() {
		// do some init
	}

	@Test
	public void testGame() {
		System.out.println("Inside testGame() for JUnit testing of full Game!");

		Scanner in = null;
		String winnerName = null;
		try {
			GameController cntrl = new GameController(new SnakesNLadderGame());
			try {
				//read from file
				in = new Scanner(SnakeNLadderGameTest.class.getResourceAsStream(propFileName));
				
				winnerName = cntrl.start(in, false);
			} finally {
				in.close();
			}
			
			assertEquals(winnerName != null && !winnerName.isEmpty(), true);

		} catch (NumberFormatException e) {

			//e.printStackTrace();
			System.out.println("\nGame Failure!");
		} catch (Exception e) {

			//e.printStackTrace();
			System.out.println("\nGame Failure due to invalid Input!");

		} finally {
			in.close();
		}

	}

}
