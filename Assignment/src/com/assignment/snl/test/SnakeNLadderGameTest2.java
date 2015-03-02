package com.assignment.snl.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.assignment.snl.controller.SnakesNLadderGame;

public class SnakeNLadderGameTest2 extends TestCase {

	private SnakesNLadderGame game = null;
	
	private int maxNumberOnDie = 0;
	
	// assigning the values
	// automatic call while testcase initialization
	protected void setUp() {
		System.out.println("In Setup");

		game = new SnakesNLadderGame();
			
		maxNumberOnDie = 6;
	}

	public int testRollDice() {
		System.out.println("Inside testRollDice() for JUnit testing of parts of Game!");

		System.out.println("Testing roll the Dice!");

		game.setMaxNumberOnDie(maxNumberOnDie);
		int dicefacevalue = game.rollTheDice();
		
		// diceFaceValue in maxNoDice range
		assertTrue(maxNumberOnDie >= dicefacevalue && dicefacevalue > 0);

		System.out.println("Game's roll the Dice is working fine!");
		
		return dicefacevalue;
	}
	
	@Test
	public void testMovePlayer() {
		System.out.println("Inside testMovePlayer() for JUnit testing of parts of Game!");

		System.out.println("Testing move the player!");

		int diceDigit = testRollDice();
		int newPosition = game.movePlayer("TestPlayer",0, diceDigit);
		
		assertTrue(newPosition <= game.getBoard().getTotalBoardItems() && newPosition > 0);

		System.out.println("Game's move player is working fine!");

	}

}
