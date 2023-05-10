package catan.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Road;
import catan.data.Settlement;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Game;
//CHECKSTYLE:OFF: checkstyle:magicnumber
public class SettlementBuildTests {

	private List<Player> createPlayerWithSettlementResources() {
		Player player = new Player(1);
		player.modifyResource(ResourceType.SHEEP, 1);
		player.modifyResource(ResourceType.WHEAT, 1);
		player.modifyResource(ResourceType.BRICK, 1);
		player.modifyResource(ResourceType.WOOD, 1);

		List<Player> p = new ArrayList<>();
		p.add(player);

		return p;
	}

	@Test
	void createNewSettlement_AddsSettlementToBoard() {
		Board b = new Board();
		Coordinate c = new Coordinate(1, 0, 0);
		b.createNewSettlement(c, 1);
		List<Settlement> settlements = b.getSettlements();
		assertEquals(1, settlements.size());

		Settlement s = settlements.get(0);
		assert (s.getLocation().equals(c));
		assert (s.getOwner() == 1);
		assert (!s.isCity());
	}

	@Test
	void buildSettlement_NotEnoughResources_ReturnsFalse() {
		Player player = new Player(1);
		List<Player> players = new ArrayList<>();
		players.add(player);
		Board b = new Board();
		Game game = new Game(b, players);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.getPlayerId(), c));
	}

	@Test
	void buildSettlement_NotEnoughResources_EnoughWood_ReturnsFalse(){
		Player player = new Player(1);
		player.modifyResource(ResourceType.WOOD, 1);
		List<Player> players = new ArrayList<>();
		players.add(player);
		Board b = new Board();
		Game game = new Game(b, players);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.getPlayerId(), c));
	}

	@Test
	void buildSettlement_NotEnoughResources_EnoughWoodBrick_ReturnsFalse(){
		Player player = new Player(1);
		player.modifyResource(ResourceType.WOOD, 1);
		player.modifyResource(ResourceType.BRICK, 1);
		List<Player> players = new ArrayList<>();
		players.add(player);
		Board b = new Board();
		Game game = new Game(b, players);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.getPlayerId(), c));
	}

	@Test
	void buildSettlement_NotEnoughResources_EnoughWoodBrickSheep_ReturnsFalse(){
		Player player = new Player(1);
		player.modifyResource(ResourceType.WOOD, 1);
		player.modifyResource(ResourceType.BRICK, 1);
		player.modifyResource(ResourceType.SHEEP, 1);
		List<Player> players = new ArrayList<>();
		players.add(player);
		Board b = new Board();
		Game game = new Game(b, players);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.getPlayerId(), c));
	}

	@Test
	void buildSettlement_PosIsOccupied_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(1, 0, 0),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.replay(b);
		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c));
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_NotConnectedToARoad_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 2, 0),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getRoads()).andReturn(new ArrayList<>());
		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c));
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_ConnectedToARoadNotOwned_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 2, 0),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 2));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c));
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_ConnectedToARoadOwnedByPlayer_IsAdjacentToASettlement_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, 0),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		EasyMock.replay(b);
		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c));
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_ConnectedToARoadOwnedByPlayer_IsNotAdjacentToASettlement_BuildsSettlement() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, -1),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1));
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(2, 0, -1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewSettlement(new Coordinate(1, 0, 0), 1);
		EasyMock.expectLastCall();
		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WHEAT));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.SHEEP));
		assertEquals(1, player.get(0).getVictoryPoints());
		assertEquals(1, player.get(0).getInternalVictoryPoints());
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_ConnectedToARoadOwnedByPlayer_IsNotAdjacentToASettlement_RoadEnd_BuildsSettlement(){
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, -1),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(1, 0, 0), 1));
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(2, 0, -1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewSettlement(new Coordinate(1, 0, 0), 1);
		EasyMock.expectLastCall();
		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WHEAT));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.SHEEP));
		assertEquals(1, player.get(0).getVictoryPoints());
		assertEquals(1, player.get(0).getInternalVictoryPoints());
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_NotConnectedToARoadOwnedByPlayer_OtherRoadsAreOwned_ReturnsFalse(){
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, -1),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(2, 0, -1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		EasyMock.expectLastCall();
		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c));
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_ConnectedToMultipleRoadsWhereOneIsOwned_BuildsSettlement() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(2, 0, -1),
				player.get(0).getPlayerId(), false));
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1));
		roads.add(new Road(new Coordinate(2, 0, 0), new Coordinate(2, 0, -1), 1));
		roads.add(new Road(new Coordinate(1, 0, 0), new Coordinate(-1, 0, 0), 0));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		b.createNewSettlement(new Coordinate(1, 0, 0), 1);
		EasyMock.expectLastCall();
		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, 0);

		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.WHEAT));
		assertEquals(0, player.get(0).getResourceCount(ResourceType.SHEEP));
		assertEquals(1, player.get(0).getVictoryPoints());
		assertEquals(1, player.get(0).getInternalVictoryPoints());
		EasyMock.verify(b);
	}

	@Test
	void buildSettlement_onTwoToOneWheatPort_playerHasImprovedWheatTrade() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(1, 0, -2), new Coordinate(1, 0, -3), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(1, 0, -3), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(1, 0, -3);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));

		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.WHEAT));
	}

	@Test
	void buildSettlement_onTwoToOneOrePort_playerHasImprovedOreTrade() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(2, 0, -1), new Coordinate(3, 0, -1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(3, 0, -1), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(3, 0, -1);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));

		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.ORE));
	}

	@Test
	void buildSettlement_onTwoToOneSheepPort_playerHasImprovedSheepTrade() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(0, 2, 1), new Coordinate(0, 3, 1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(0, 3, 1), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(0, 3, 1);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));

		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.SHEEP));
	}


	@Test
	void buildSettlement_onTwoToOneBrickPort_playerHasImprovedBrickTrade() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(-2, -1, 0), new Coordinate(-3, -1, 0), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(-3, -1 ,0), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(-3, -1, 0);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));

		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.BRICK));
	}

	@Test
	void buildSettlement_onTwoToOneWoodPort_playerHasImprovedWoodTrade() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(-1, -2, 0), new Coordinate(-1, -3, 0), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(-1, -3, 0), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(-1, -3, 0);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));

		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.WOOD));
	}

	@Test
	void buildSettlement_onThreeToOneWoodPort_playerHasImprovedTrades() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(3, 1, 0), new Coordinate(3, 2, 0), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(3, 2, 0), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c = new Coordinate(3, 2, 0);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c));

		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WOOD));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.BRICK));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.ORE));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.SHEEP));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WHEAT));
	}

	@Test
	void buildSettlement_3To1ClaimedBefore2To1_playerHasImprovedTrades() {
		List<Player> player = createPlayerWithSettlementResources();

		player.get(0).modifyResource(ResourceType.SHEEP, 1);
		player.get(0).modifyResource(ResourceType.WHEAT, 1);
		player.get(0).modifyResource(ResourceType.BRICK, 1);
		player.get(0).modifyResource(ResourceType.WOOD, 1);

		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(3, 1, 0), new Coordinate(3, 2, 0), 1));
		roads.add(new Road(new Coordinate(2, 0, -1), new Coordinate(3, 0, -1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(3, 2, 0), 1);
		EasyMock.expectLastCall();

		b.createNewSettlement(new Coordinate(3, 0, -1), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c1 = new Coordinate(3, 2, 0);
		Coordinate c2 = new Coordinate(3, 0, -1);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c1));
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c2));

		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WOOD));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.BRICK));
		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.ORE));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.SHEEP));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WHEAT));
	}

	@Test
	void buildSettlement_2To1ClaimedBefore3To1_playerHasImprovedTrades() {
		List<Player> player = createPlayerWithSettlementResources();

		player.get(0).modifyResource(ResourceType.SHEEP, 1);
		player.get(0).modifyResource(ResourceType.WHEAT, 1);
		player.get(0).modifyResource(ResourceType.BRICK, 1);
		player.get(0).modifyResource(ResourceType.WOOD, 1);

		Board b = EasyMock.mock(Board.class);

		List<Settlement> settlements = new ArrayList<>();
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);

		List<Road> roads = new ArrayList<>();
		roads.add(new Road(new Coordinate(3, 1, 0), new Coordinate(3, 2, 0), 1));
		roads.add(new Road(new Coordinate(2, 0, -1), new Coordinate(3, 0, -1), 1));
		EasyMock.expect(b.getRoads()).andReturn(roads);
		EasyMock.expect(b.getRoads()).andReturn(roads);

		b.createNewSettlement(new Coordinate(3, 2, 0), 1);
		EasyMock.expectLastCall();

		b.createNewSettlement(new Coordinate(3, 0, -1), 1);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		Game game = new Game(b, player);

		Coordinate c1 = new Coordinate(3, 2, 0);
		Coordinate c2 = new Coordinate(3, 0, -1);
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c2));
		assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c1));

		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WOOD));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.BRICK));
		assertEquals(2, player.get(0).getTradeValues().get(ResourceType.ORE));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.SHEEP));
		assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WHEAT));
	}
}
