package catan.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import catan.Coordinate;
import catan.Game;
import catan.Board;
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

		List<Road> roads = b.getRoads();
		assertEquals(1, roads.size());
		assertEquals(start, roads.get(0).getStart());
		assertEquals(end, roads.get(0).getEnd());
		assertEquals(1, roads.get(0).getOwner());
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
	void buildRoad_StartEndIsSame_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = new Board();
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(1, 0, 0)));
	}

	@Test
	void buildRoad_NotEnoughWood_ReturnsFalse() {
		Player player = new Player(1);
		player.modifyResource(ResourceType.BRICK, 1);
		List<Player> players = new ArrayList<>();
		players.add(player);

		Board b = new Board();

		Game g = new Game(b, players);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
	}

	@Test
	void buildRoad_NotEnoughBrick_ReturnsFalse() {
		Player player = new Player(1);
		player.modifyResource(ResourceType.WOOD, 1);
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
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1));
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(1, 0, 0), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
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
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(1, 0, 0), 2, false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(new ArrayList<>());
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
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, 0), 2, false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(new ArrayList<>());
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
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(1, 0, 0), 1, false));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(new ArrayList<>());
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.expectLastCall();
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
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, 0), 1, false));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(new ArrayList<>());
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
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 2));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
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
		List<Settlement> settlements = new ArrayList<>();
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(1, 0, 0), 2));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(roads);
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
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(0, 0, -1), 1));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.expectLastCall();
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
	void buildRoad_RoadsConnectedAtEndPoints_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 1, 0), new Coordinate(2, 0, 0), 1));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.expectLastCall();
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
	void buildRoad_RoadsConnectedStartEnd_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 1, 0), new Coordinate(2, 0, 0), 1));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(1, 0, 0));
		EasyMock.expectLastCall();
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertTrue(g.buildRoad(1,
				new Coordinate(2, 0, 0),
				new Coordinate(1, 0, 0)));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));

		EasyMock.verify(b);
	}

	@Test
	void buildRoad_EndHasRoadOwnedByPlayer_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(2, 1, 0), 1));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.expectLastCall();
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
	void buildRoad_NoEndsConnected_OtherRoadsOwnedByPlayer_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(0, -1, 0), new Coordinate(0, 0, -1), 1));
		roads.add(new Road(new Coordinate(1, 2, 0), new Coordinate(2, 1, 0), 1));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
		EasyMock.replay(b);
		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));
		EasyMock.verify(b);
	}

	// This handles an edge case where the road is connected to a player road at one
	// end, but the other end is connected to another player's road (this is a legal
	// case that should be tested).
	@Test
	void buildRoad_OneEndSatisfiesConditionOtherFails_BuildsRoad() {
		List<Player> player = createPlayerWithSettlementResources();
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(0, 0, -1), 1));
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(2, 1, 0), 2));
		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
		EasyMock.expectLastCall();
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
	void buildRoad_NotConnectedToSettlement_OtherSettlementsOwned_ReturnFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(0, 0, -1), 1, false));

		Board b = EasyMock.createMock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(new ArrayList<>());
		EasyMock.replay(b);

		Game g = new Game(b, player);

		assertFalse(g.buildRoad(1,
				new Coordinate(1, 0, 0),
				new Coordinate(2, 0, 0)));

		EasyMock.verify(b);
	}

}
