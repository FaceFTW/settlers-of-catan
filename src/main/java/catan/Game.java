package catan;

import java.util.Random;

public class Game {
	public static final int MAX_DICE_VALUE = 5;
	public static final int DEFAULT_NUM_PLAYERS = 4;

	private Random random;
	private int currentTurn;
	private int numberOfPlayers;

	public Game() {
		this.random = new Random();
		this.numberOfPlayers = DEFAULT_NUM_PLAYERS;
	}

	/**
	 * DI constructor, only injects the random object
	 *
	 * @param random
	 */
	public Game(Random random) {
		this.random = random;
	}

	/**
	 * DI constructor, only injects the number of players and the current turn
	 *
	 * @param numberOfPlayers
	 */
	public Game(int numberOfPlayers, int currentTurn) {
		this.numberOfPlayers = numberOfPlayers;
		this.currentTurn = currentTurn;
	}

	public final int rollDie() {
		int result = random.nextInt(MAX_DICE_VALUE) + 1;
		result += random.nextInt(MAX_DICE_VALUE) + 1;
		return result;
	}

	/**
	 * Increments the current turn, fallsback to 1 if the current turn is greater
	 * than the number of players
	 */
	public void nextTurn() {
		this.currentTurn++;
		if (this.currentTurn > this.numberOfPlayers) {
			this.currentTurn = 1;
		}
	}

	/**
	 * @return Gets the current turn
	 */
	public int getTurn() {
		return this.currentTurn;
	}

}
