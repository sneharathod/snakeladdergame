package com.assignment.snl.model;

import java.io.IOException;
import java.util.Hashtable;

import com.assignment.snl.util.GameConstants;
import com.assignment.snl.util.GamePropertyReader;

public class Board {
	private Hashtable<Integer, Integer> snakesLadders = new Hashtable<Integer, Integer>();

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

		this.setSnakesLadders(createHashTable(snakesStr));
		this.getSnakesLadders().putAll(createHashTable(ladderStr));
		
		//System.out.println("size of snakesnladder"+this.getSnakesLadders().size());

	}

	private static Hashtable<Integer, Integer> createHashTable(String str) {
		Hashtable<Integer, Integer> hashTable = new Hashtable<Integer, Integer>();
		String[] arr = str.split(";");

		for (String item : arr) {
			item = item.replace("(", "").replace(")", "");
			String[] itemArr = item.split(",");

			/* snakes/ladders tip toe put in table */
			hashTable.put(Integer.parseInt(itemArr[0]),
					Integer.parseInt(itemArr[1]));
		}

		return hashTable;
	}

	public int getTotalBoardItems() {
		return totalBoardItems;
	}

	public void setTotalBoardItems(int totalBoardItems) {
		this.totalBoardItems = totalBoardItems;
	}

	public Hashtable<Integer, Integer> getSnakesLadders() {
		return snakesLadders;
	}

	public void setSnakesLadders(Hashtable<Integer, Integer> snakesLadders) {
		this.snakesLadders = snakesLadders;
	}
}
