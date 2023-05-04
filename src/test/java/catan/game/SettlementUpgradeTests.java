package catan.game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import catan.Coordinate;
import catan.Game;
import catan.Board;
import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Settlement;

public class SettlementUpgradeTests {
	// CHECKSTYLE:OFF: checkstyle:magicnumber
	private List<Player> createPlayerWithUpgradeResources() {
		Player player = new Player(1);
		player.modifyResource(ResourceType.WHEAT, 2);
		player.modifyResource(ResourceType.ORE, 3);

		List<Player> p = new ArrayList<>();
		p.add(player);

		return p;
	}

	@Test
	void boardUpgradeSettlement_NoSettlementAtPos_DoesNotDoAnything() {
		Board b = new Board();
		assertDoesNotThrow(() -> b.upgradeSettlement(new Coordinate(1, 0, 0)));
	}

	@Test
	void boardUpgradeSettlement_SettlementAtPos_UpgradesSettlement() {
		Board b = new Board();
		b.createNewSettlement(new Coordinate(1, 0, 0), 1);
		b.upgradeSettlement(new Coordinate(1, 0, 0));

		List<Settlement> settlements = b.getSettlements();

		assertEquals(1, settlements.size());
		assertTrue(settlements.get(0).isCity());
	}

	@Test
	void upgradeSettlement_NotEnoughResources_ReturnsFalse() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(1));
		Board b = EasyMock.mock(Board.class);
		EasyMock.replay(b);
		Game game = new Game(b, players);

		assertFalse(game.upgradeSettlement(1, new Coordinate(1, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void upgradeSettlement_NoSettlementAtPos_ReturnsFalse() {
		List<Player> p = createPlayerWithUpgradeResources();
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(new ArrayList<>());
		EasyMock.replay(b);
		Game game = new Game(b, p);

		assertFalse(game.upgradeSettlement(1, new Coordinate(1, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void upgradeSettlement_SettlementAtPosNotOwnedByPlayer_ReturnsFalse() {
		List<Player> p = createPlayerWithUpgradeResources();
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(1, 0, 0), 2, false));
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.replay(b);
		Game game = new Game(b, p);

		assertFalse(game.upgradeSettlement(1, new Coordinate(1, 0, 0)));

		EasyMock.verify(b);

	}

	@Test
	void upgradeSettlement_SettlementAtPosIsACity_ReturnsFalse() {
		List<Player> p = createPlayerWithUpgradeResources();
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(1, 0, 0), 1, true));
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		EasyMock.replay(b);
		Game game = new Game(b, p);

		assertFalse(game.upgradeSettlement(1, new Coordinate(1, 0, 0)));

		EasyMock.verify(b);
	}

	@Test
	void upgradeSettlement_OwnedByPlayer_UpgradesSettlement() {
		List<Player> p = createPlayerWithUpgradeResources();
		List<Settlement> settlements = new ArrayList<>();
		settlements.add(new Settlement(new Coordinate(1, 0, 0), 1, false));
		Board b = EasyMock.mock(Board.class);
		EasyMock.expect(b.getSettlements()).andReturn(settlements);
		b.upgradeSettlement(settlements.get(0).getLocation());
		EasyMock.expectLastCall();
		EasyMock.replay(b);

		Game game = new Game(b, p);

		assertTrue(game.upgradeSettlement(1, new Coordinate(1, 0, 0)));
		assertEquals(0, p.get(0).getResourceCount(ResourceType.WHEAT));
		assertEquals(0, p.get(0).getResourceCount(ResourceType.ORE));

		EasyMock.verify(b);

	}

}
