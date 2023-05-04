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
		Settlement[] settlements = b.getSettlements();
		assertEquals(1, settlements.length);

		Settlement s = settlements[0];
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
	void buildSettlement_PosIsOccupied_ReturnsFalse() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements())
				.andReturn(new Settlement[] {
						new Settlement(new Coordinate(1, 0, 0),
								player.get(0).getPlayerId(),
								false) });
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
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 2, 0), 0, false)
		});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {});
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
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 2, 0), 0, false)
		});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 0)
		});
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
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 0, 0), 1, false)
		});

		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1)
		});
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
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 0, -1), 1, false)
		});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1),
				new Road(new Coordinate(2, 0, 0), new Coordinate(2, 0, -1), 1)
		});

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
	void buildSettlement_ConnectedToMultipleRoadsWhereOneIsOwned_BuildsSettlement() {
		List<Player> player = createPlayerWithSettlementResources();
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new Settlement[] {
				new Settlement(new Coordinate(2, 0, -1), 1, false)
		});
		EasyMock.expect(b.getRoads()).andReturn(new Road[] {
				new Road(new Coordinate(1, 0, 0), new Coordinate(2, 0, 0), 1),
				new Road(new Coordinate(2, 0, 0), new Coordinate(2, 0, -1), 1),
				new Road(new Coordinate(1, 0, 0), new Coordinate(-1, 0, 0), 0)
		});

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

}
