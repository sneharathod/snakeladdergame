package com.assignment.snl.model;

import java.io.IOException;
import java.util.Hashtable;

import com.assignment.snl.util.GameConstants;
import com.assignment.snl.util.GamePropertyReader;

public class Board {
	private Hashtable<String, String> snakes = new Hashtable<String, String>();

	private Hashtable<String, String> ladders = new Hashtable<String, String>();

	private int totalBoardItems = 0;

	private GamePropertyReader reader;

	/**
	 * Fill snakes and ladders aka board properties from game.properties file
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public Board() throws NumberFormatException, IOException {

		reader = new GamePropertyReader(GameConstants.GAMEPROPERTYFILE);

		int boardLength = Integer.parseInt(reader
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

}
