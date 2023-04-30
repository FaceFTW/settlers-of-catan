package catan;

import catan.data.ResourceType;
import catan.data.Tile;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
	@Test
	public void testConstructor() {
		// RECORD
		Random r = EasyMock.createStrictMock(Random.class);
		for (int i = 19; i > 0; i--) {
			EasyMock.expect(r.nextInt(i)).andReturn(0);
		}

		// REPLAY
		EasyMock.replay(r);
		Board board = new Board(r);

		for (int i = 0; i < 19; i++) {
			assertEquals(Utils.ALL_TILES_RESOURCES[i], board.tileList[i].getResourceType());
			assertEquals(Utils.TILES_SPIRAL_LOCATION[i], board.tileList[i].getPosition());
		}

		int desertEffect = 0;
		for (int i = 0; i < 18; i++) {
			if (board.tileList[i].getResourceType() == ResourceType.DESERT) {
				desertEffect = -1;
				continue;
			}
			assertEquals(Utils.TILES_ROLL_NUMBERS[i + desertEffect],
						 board.tileList[i].getDieRoll());
		}


		// VERIFY
		EasyMock.verify(r);
	}

	@Test
	public void testCreateBoard_lookAtSpiralStart_checkCorrectCornerPlacement() {
		ArrayList<Coordinate> expected = new ArrayList<>();
		expected.add(new Coordinate(-2, 0, 1));
		expected.add(new Coordinate(-1, 0, 2));
		expected.add(new Coordinate(-1, 0, 3));
		expected.add(new Coordinate(-2, 0, 3));
		expected.add(new Coordinate(-3, 0, 2));
		expected.add(new Coordinate(-3, 0, 1));

		Board board = new Board(new Random());
		Tile tileInQuestion = board.tileList[0];

		for (Coordinate c: tileInQuestion.getCorners()) {
			assertTrue(expected.remove(c));
		}

		assertTrue(expected.isEmpty());
	}

	@Test
	public void testCreateBoard_lookAtSpiralEnd_checkCorrectCornerPlacement() {
		ArrayList<Coordinate> expected = new ArrayList<>();
		expected.add(new Coordinate(-1, 0, 0));
		expected.add(new Coordinate(1, 0, 0));
		expected.add(new Coordinate(0, -1, 0));
		expected.add(new Coordinate(0, 1, 0));
		expected.add(new Coordinate(0, 0, -1));
		expected.add(new Coordinate(0, 0, 1));

		Board board = new Board(new Random());
		Tile tileInQuestion = board.tileList[18];

		for (Coordinate c: tileInQuestion.getCorners()) {
			assertTrue(expected.remove(c));
		}

		assertTrue(expected.isEmpty());
	}
}
