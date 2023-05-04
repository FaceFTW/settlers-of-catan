package catan.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import catan.Coordinate;
import catan.Game;
import catan.data.Board;
import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Road;
import catan.data.Settlement;

public class RoadBuildTests {

	private List<Player> createPlayerWithSettlementResources() {
		Player player = new Player(1);
		player.modifyResource(ResourceType.BRICK, 1);
		player.modifyResource(ResourceType.WOOD, 1);

		List<Player> p = new ArrayList<>();
		p.add(player);

		return p;
	}

	@Test
	void boardBuildRoad_BuildsRoad() {
		Board b = new Board();
		Coordinate start = new Coordinate(1, 0, 0);
		Coordinate end = new Coordinate(1, 0, 1);
		b.createNewRoad(1, start, end);

		Road[] roads = b.getRoads();
		assertEquals(1, roads.length);
		assertEquals(start, roads[0].getStart());
		assertEquals(end, roads[0].getEnd());
		assertEquals(1, roads[0].getOwner());
	}

	@Test
	void buildRoad_NotEnoughResources_ReturnsFalse() {
		Player player = new Player(1);
		List<Player> players = new ArrayList<>();
		players.add(player);

		Board b = new Board();

		Game g = new Game(b, players);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
	}

	@Test
	void buildRoad_LengthIsNotOne_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = new Board();
		Game g = new Game(b, player);

		assertEquals(false,
				g.buildRoad(1,
						new Coordinate(1, 0, 0),
						new Coordinate(2, 1, 0)));
	}

	@Test
	void buildRoad_NoEndConnected_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = new Board();
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
	}

	@Test
	void buildRoad_BothStartEndOccupiedByRoad_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1) });
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		EasyMock.verify(b);
	}

	@Test
	void buildRoad_StartHasSettlementNotOwnedByPlayer_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(1, 0, 0), 2, false) });
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {});
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_EndHasSettlementNotOwnedByPlayer_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 0, 0), 2, false) });
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {});
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_StartHasSettlementOwnedByPlayer_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(1, 0, 0), 1, false) });
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {});
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertTrue(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_EndHasSettlementOwnedByPlayer_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 0, 0), 1, false) });
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {});
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertTrue(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_StartHasRoadNotOwnedByPlayer_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(0, 0, -1), 2) });
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_EndHasRoadNotOwnedByPlayer_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(2, 0, 0), new Coordinate(2, 1, 0), 2) });
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_StartHasRoadOwnedByPlayer_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(0, 0, -1), 1) });
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertTrue(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_EndHasRoadOwnedByPlayer_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(2, 0, 0), new Coordinate(2, 1, 0), 1) });
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));

		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertTrue(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));

		EasyMock.verify(b);
	}

	// This handles an edge case where the road is connected to a player road at one
	// end, but the other end is connected to another player's road (this is a legal
	// case that should be tested).
	@Test
	void buildRoad_OneEndSatisfiesConditionOtherFails_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(0, 0, -1), 1),
				new Road(new Coordinate(2, 0, 0), new Coordinate(2, 1, 0), 2) });
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertTrue(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));

		EasyMock.verify(b);
	}

}
