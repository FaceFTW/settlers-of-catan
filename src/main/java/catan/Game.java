package catan;

import java.util.Random;

public class Game {
	public static final int MAX_DICE_VALUE = 5;

	private Random random;

	public Game() {
		this.random = new Random();
	}

	/**
	 * DI constructor, only injects the random object
	 *
	 * @param random
	 */
	public Game(Random random) {
		this.random = random;
	}

	public final int rollDie() {
		int result = random.nextInt(MAX_DICE_VALUE) + 1;
		result += random.nextInt(MAX_DICE_VALUE) + 1;
		return result;
	}

}
