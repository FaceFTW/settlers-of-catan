package catan.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import catan.Game;
import catan.data.Player;
import catan.data.ResourceType;
import catan.data.ResourcesGroup;
import catan.data.TradeOffer;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class PlayerResourceTests {
	@Test
	void exchangeResources_ExchangeOneofDifferentResources() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		for (ResourceType type : ResourceType.values()) {
			player1.modifyResource(type, 5);
			player2.modifyResource(type, 5);
		}

		Game game = new Game();
		game.addPlayer(player1);
		game.addPlayer(player2);

		game.exchangeResources(1, 2, new TradeOffer(
				new ResourcesGroup(1, 1, 0, 0, 0),
				new ResourcesGroup(0, 0, 1, 1, 1)));

		assertEquals(4, player1.getResourceCount(ResourceType.WOOD));
		assertEquals(4, player1.getResourceCount(ResourceType.BRICK));
		assertEquals(6, player1.getResourceCount(ResourceType.SHEEP));
		assertEquals(6, player1.getResourceCount(ResourceType.WHEAT));
		assertEquals(6, player1.getResourceCount(ResourceType.ORE));
		assertEquals(6, player2.getResourceCount(ResourceType.WOOD));
		assertEquals(6, player2.getResourceCount(ResourceType.BRICK));
		assertEquals(4, player2.getResourceCount(ResourceType.SHEEP));
		assertEquals(4, player2.getResourceCount(ResourceType.WHEAT));
		assertEquals(4, player2.getResourceCount(ResourceType.ORE));
	}

	@Test
	void exchangeResources_ExchangeOneofDifferentResources_OtherDirection() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		for (ResourceType type : ResourceType.values()) {
			player1.modifyResource(type, 5);
			player2.modifyResource(type, 5);
		}

		Game game = new Game();
		game.addPlayer(player1);
		game.addPlayer(player2);

		game.exchangeResources(2, 1, new TradeOffer(
				new ResourcesGroup(1, 1, 0, 0, 0),
				new ResourcesGroup(0, 0, 1, 1, 1)));

		assertEquals(6, player1.getResourceCount(ResourceType.WOOD));
		assertEquals(6, player1.getResourceCount(ResourceType.BRICK));
		assertEquals(4, player1.getResourceCount(ResourceType.SHEEP));
		assertEquals(4, player1.getResourceCount(ResourceType.WHEAT));
		assertEquals(4, player1.getResourceCount(ResourceType.ORE));
		assertEquals(4, player2.getResourceCount(ResourceType.WOOD));
		assertEquals(4, player2.getResourceCount(ResourceType.BRICK));
		assertEquals(6, player2.getResourceCount(ResourceType.SHEEP));
		assertEquals(6, player2.getResourceCount(ResourceType.WHEAT));
		assertEquals(6, player2.getResourceCount(ResourceType.ORE));
	}

	@Test
	void exchangeResources_ExchangeOneTypeOfResource() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		for (ResourceType type : ResourceType.values()) {
			player1.modifyResource(type, 5);
			player2.modifyResource(type, 5);
		}

		Game game = new Game();
		game.addPlayer(player1);
		game.addPlayer(player2);

		game.exchangeResources(1, 2, new TradeOffer(
				new ResourcesGroup(2, 0, 0, 0, 0),
				new ResourcesGroup(0, 0, 3, 0, 0)));

		assertEquals(3, player1.getResourceCount(ResourceType.WOOD));
		assertEquals(8, player1.getResourceCount(ResourceType.SHEEP));
		assertEquals(7, player2.getResourceCount(ResourceType.WOOD));
		assertEquals(2, player2.getResourceCount(ResourceType.SHEEP));
	}

	@Test
	void exchangeResources_ExchangeOneTypeOfResource_OtherDirection() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		for (ResourceType type : ResourceType.values()) {
			player1.modifyResource(type, 5);
			player2.modifyResource(type, 5);
		}

		Game game = new Game();
		game.addPlayer(player1);
		game.addPlayer(player2);

		game.exchangeResources(2, 1, new TradeOffer(
				new ResourcesGroup(2, 0, 0, 0, 0),
				new ResourcesGroup(0, 0, 3, 0, 0)));

		assertEquals(7, player1.getResourceCount(ResourceType.WOOD));
		assertEquals(2, player1.getResourceCount(ResourceType.SHEEP));
		assertEquals(3, player2.getResourceCount(ResourceType.WOOD));
		assertEquals(8, player2.getResourceCount(ResourceType.SHEEP));
	}

	@Test
	public void exchangeResources_MultipleTypesMultipleAmounts_DistributesAsExpected() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		for (ResourceType type : ResourceType.values()) {
			player1.modifyResource(type, 10);
			player2.modifyResource(type, 10);
		}

		Game game = new Game();
		game.addPlayer(player1);
		game.addPlayer(player2);

		game.exchangeResources(1, 2, new TradeOffer(
				new ResourcesGroup(2, 1, 4, 3, 0),
				new ResourcesGroup(0, 0, 0, 0, 1)));

		assertEquals(8, player1.getResourceCount(ResourceType.WOOD));
		assertEquals(9, player1.getResourceCount(ResourceType.BRICK));
		assertEquals(6, player1.getResourceCount(ResourceType.SHEEP));
		assertEquals(7, player1.getResourceCount(ResourceType.WHEAT));
		assertEquals(11, player1.getResourceCount(ResourceType.ORE));
		assertEquals(12, player2.getResourceCount(ResourceType.WOOD));
		assertEquals(11, player2.getResourceCount(ResourceType.BRICK));
		assertEquals(14, player2.getResourceCount(ResourceType.SHEEP));
		assertEquals(13, player2.getResourceCount(ResourceType.WHEAT));
		assertEquals(9, player2.getResourceCount(ResourceType.ORE));
	}

	@Test
	void exchangeResources_MultipleTypesMultipleAmounts_OtherDirection_DistributesAsExpected(){
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		for (ResourceType type : ResourceType.values()) {
			player1.modifyResource(type, 10);
			player2.modifyResource(type, 10);
		}

		Game game = new Game();
		game.addPlayer(player1);
		game.addPlayer(player2);

		game.exchangeResources(2, 1, new TradeOffer(
				new ResourcesGroup(0, 0, 4, 3, 3),
				new ResourcesGroup(1, 2, 0, 0, 0)));

		assertEquals(9, player1.getResourceCount(ResourceType.WOOD));
		assertEquals(8, player1.getResourceCount(ResourceType.BRICK));
		assertEquals(14, player1.getResourceCount(ResourceType.SHEEP));
		assertEquals(13, player1.getResourceCount(ResourceType.WHEAT));
		assertEquals(13, player1.getResourceCount(ResourceType.ORE));
		assertEquals(11, player2.getResourceCount(ResourceType.WOOD));
		assertEquals(12, player2.getResourceCount(ResourceType.BRICK));
		assertEquals(6, player2.getResourceCount(ResourceType.SHEEP));
		assertEquals(7, player2.getResourceCount(ResourceType.WHEAT));
		assertEquals(7, player2.getResourceCount(ResourceType.ORE));
	}

}
