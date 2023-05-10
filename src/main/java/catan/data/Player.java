package catan.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A data class representing a player in the game.
 * There are no unit testable methods for this class as they are simply special encapsulation
 * mechanisms for the game.
 */
public class Player {
	private final int playerId;
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	private int victoryPoints;
//	private int knightsPlayed;
	private int internalVictoryPoints;
	private Map<DevelopmentCard, Integer> developmentCards;
	private Map<ResourceType, Integer> tradeValues;

	public Player(final int id) {
		this.playerId = id;
		this.wood = 0;
		this.brick = 0;
		this.sheep = 0;
		this.wheat = 0;
		this.ore = 0;
		this.victoryPoints = 0;
//		this.knightsPlayed = 0;
		this.internalVictoryPoints = 0;
		this.developmentCards = new HashMap<>();
		for (DevelopmentCard card : DevelopmentCard.values()) {
			developmentCards.put(card, 0);
		}
		this.tradeValues = new HashMap<>();
		this.tradeValues.put(ResourceType.BRICK, 4);
		this.tradeValues.put(ResourceType.WOOD, 4);
		this.tradeValues.put(ResourceType.WHEAT, 4);
		this.tradeValues.put(ResourceType.SHEEP, 4);
		this.tradeValues.put(ResourceType.ORE, 4);
	}

	/**
	 * @return The player's id
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Getter for the amount of resources a player has of a specific type
	 *
	 * @param type The type of resource to get the count of
	 * @return The amount of resources of the given type owned by the player
	 */
	public int getResourceCount(ResourceType type) {
		switch (type) {
			case WOOD:
				return wood;
			case BRICK:
				return brick;
			case SHEEP:
				return sheep;
			case WHEAT:
				return wheat;
			case ORE:
				return ore;
			default:
				return 0;
		}
	}

	/**
	 * Adds a resource to the player's inventory. Use negative amounts to remove resources
	 *
	 * @param type The type of resource to add
	 * @param amount The amount of resources to add
	 */
	public void modifyResource(ResourceType type, int amount) {
		switch (type) {
			case WOOD:
				wood += amount;
				break;
			case BRICK:
				brick += amount;
				break;
			case SHEEP:
				sheep += amount;
				break;
			case WHEAT:
				wheat += amount;
				break;
			case ORE:
				ore += amount;
				break;
			default:
				break;
		}
	}

	/**
	 * Updates how many of a resource are needed to do a trade.
	 *
	 * @param type the resource that can be traded
	 * @param value how many it takes to trade
	 */
	public void updateTradeValue(ResourceType type, int value) {
		this.tradeValues.put(type, value);
	}

	/**
	 * @return The amount of development cards a player posesses
	 */
	public int getDevelopmentCardCount(DevelopmentCard card) {
		return developmentCards.getOrDefault(card, 0);
	}

	/**
	 * Adds a development card to the player's inventory. Use negative amounts to remove
	 * development cards
	 *
	 * @param card The type of development card to add
	 * @param amount The amount of development cards to add
	 */

	public void modifyDevelopmentCard(DevelopmentCard card, int amount) {
		if (developmentCards.containsKey(card)) {
			developmentCards.put(card, developmentCards.get(card) + amount);
		} else {
			developmentCards.put(card, amount);
		}
	}

	/**
	 * @return The amount of victory points the player has
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Sets the visible victory point count
	 *
	 * @param amount The new victory point count the player has
	 */
	public void setVictoryPoints(int amount) {
		victoryPoints = amount;
	}

	/**
	 * @return The internal victory point count, which includes development card points
	 */
	public int getInternalVictoryPoints() {
		return internalVictoryPoints;
	}

	/**
	 * Sets the internal victory point count
	 *
	 * @param amount The new internal victory point count the player has
	 */
	public void setInternalVictoryPoints(int amount) {
		internalVictoryPoints = amount;
	}


}
