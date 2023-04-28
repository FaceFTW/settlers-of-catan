package catan;

import catan.data.ResourceType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
