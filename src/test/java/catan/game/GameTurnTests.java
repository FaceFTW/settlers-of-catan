package catan.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.easymock.EasyMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import catan.Game;

public final class GameTurnTests {

	@ParameterizedTest
	@CsvSource({ "1, 1, 2",
			"1, 6, 7",
			"6, 1, 7",
			"6, 6, 12" })
	public void rollDie_CanMatchExpectedRange(int firstDie, int secondDie, int expected) {
		Random mockRandom = EasyMock.createMock(Random.class);
		EasyMock.expect(mockRandom.nextInt(Game.MAX_DICE_VALUE)).andReturn(firstDie - 1);
		EasyMock.expect(mockRandom.nextInt(Game.MAX_DICE_VALUE)).andReturn(secondDie - 1);
		EasyMock.replay(mockRandom);

		Game game = new Game(mockRandom);

		int result = game.rollDie();
		assertEquals(expected, result);
		EasyMock.verify(mockRandom);
	}

	@ParameterizedTest
	@CsvSource({ "3,1,2",
			"3,2,3",
			"3,3,1",
			"4,3,4",
			"4,4,1", })
	public void gameNextTurn_IncrementsAsExpected(int numberOfPlayers, int currentTurn, int expectedNextTurn) {
		Game game = new Game(numberOfPlayers, currentTurn);
		game.nextTurn();
		assertEquals(expectedNextTurn, game.getTurn());
	}

}
