package catan;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Tile;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
	@Test
	public void testConstructor() {
		// RECORD
		Random r = EasyMock.createStrictMock(Random.class);
		for (int i = Utils.TILES_SPIRAL_LOCATION.length; i > 0; i--) {
			EasyMock.expect(r.nextInt(i)).andReturn(0);
		}

		// REPLAY
		EasyMock.replay(r);
		Board board = new Board(r);

		for (int i = 0; i < Utils.TILES_SPIRAL_LOCATION.length; i++) {
			assertEquals(Utils.ALL_TILES_RESOURCES[i], board.getTiles()[i].getResourceType());
			assertEquals(Utils.TILES_SPIRAL_LOCATION[i], board.getTiles()[i].getPosition());
		}

		int desertEffect = 0;
		for (int i = 0; i < Utils.TILES_SPIRAL_LOCATION.length - 1; i++) {
			if (board.getTiles()[i].getResourceType() == ResourceType.DESERT) {
				desertEffect = -1;
				continue;
			}
			assertEquals(Utils.TILES_ROLL_NUMBERS[i + desertEffect],
						 board.getTiles()[i].getDieRoll());
		}


		// VERIFY
		EasyMock.verify(r);
	}

	// CHECKSTYLE:OFF: checkstyle:magicnumber
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
		Tile tileInQuestion = board.getTiles()[0];

		for (Coordinate c : tileInQuestion.getCorners()) {
			assertTrue(expected.remove(c));
		}

		assertTrue(expected.isEmpty());
	}
	// CHECKSTYLE:ON: checkstyle:magicnumber

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
		Tile tileInQuestion = board.getTiles()[Utils.TILES_SPIRAL_LOCATION.length - 1];

		for (Coordinate c : tileInQuestion.getCorners()) {
			assertTrue(expected.remove(c));
		}

		assertTrue(expected.isEmpty());
	}


	@Test
	public void testDistributeResources_twoRolledWithOnePlayer_givePlayerOneWood() {
		Random r = EasyMock.createStrictMock(Random.class);
		for (int i = Utils.TILES_SPIRAL_LOCATION.length; i > 0; i--) {
			EasyMock.expect(r.nextInt(i)).andReturn(0);
		}

		Player p1 = EasyMock.createStrictMock(Player.class);
		EasyMock.expect(p1.getPlayerId()).andReturn(1);
		p1.modifyResource(ResourceType.WOOD, 1);

		List<Player> players = new ArrayList<>();
		players.add(p1);

		EasyMock.replay(p1);
		EasyMock.replay(r);

		Board board = new Board(r);

		board.createNewSettlement(new Coordinate(0, 2, 1), 1);

		board.distributeResources(players, 2);

		// VERIFY
		EasyMock.verify(r);
		EasyMock.verify(p1);
	}

	@Test
	public void testDistributeResources_threeRolledWithOnePlayer_givePlayerTwoWood() {
		Random r = EasyMock.createStrictMock(Random.class);
		for (int i = Utils.TILES_SPIRAL_LOCATION.length; i > 0; i--) {
			EasyMock.expect(r.nextInt(i)).andReturn(0);
		}

		Player p1 = EasyMock.createStrictMock(Player.class);
		EasyMock.expect(p1.getPlayerId()).andReturn(1);
		p1.modifyResource(ResourceType.WOOD, 2);

		List<Player> players = new ArrayList<>();
		players.add(p1);

		EasyMock.replay(p1);
		EasyMock.replay(r);

		Board board = new Board(r);

		Coordinate settlementLoc = new Coordinate(2, 1, 0);
		board.createNewSettlement(settlementLoc, 1);
		board.upgradeSettlement(settlementLoc);

		board.distributeResources(players, 3);

		// VERIFY
		EasyMock.verify(r);
		EasyMock.verify(p1);
	}

	@Test
	public void testDistributeResources_sevenRolledWithTwoPlayers_nothingHappensToPlayers() {
		Random r = EasyMock.createStrictMock(Random.class);
		for (int i = Utils.TILES_SPIRAL_LOCATION.length; i > 0; i--) {
			EasyMock.expect(r.nextInt(i)).andReturn(0);
		}

		Player p1 = EasyMock.createStrictMock(Player.class);
		Player p2 = EasyMock.createStrictMock(Player.class);

		List<Player> players = new ArrayList<>();
		players.add(p1);
		players.add(p2);

		EasyMock.replay(p1);
		EasyMock.replay(p2);
		EasyMock.replay(r);

		Board board = new Board(r);

		Coordinate p1SettlementLoc = new Coordinate(-2, 0, 1);
		Coordinate p2CityLoc = new Coordinate(-2, 0, 3);
		board.createNewSettlement(p1SettlementLoc, 1);
		board.createNewSettlement(p2CityLoc, 2);
		board.upgradeSettlement(p2CityLoc);

		board.distributeResources(players, 3);

		// VERIFY
		EasyMock.verify(r);
		EasyMock.verify(p1);
	}
}
