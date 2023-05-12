package catan.data;

import catan.logic.Utils;

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
	private Map<ResourceType, Integer> tradeValues;

	public Player(final int id) {
		this.playerId = id;
		this.wood = 0;
		this.brick = 0;
		this.sheep = 0;
		this.wheat = 0;
		this.ore = 0;
		this.victoryPoints = 0;
		this.tradeValues = new HashMap<>();
		this.tradeValues.put(ResourceType.BRICK, Utils.DEFAULT_PORT_REQUIREMENT);
		this.tradeValues.put(ResourceType.WOOD, Utils.DEFAULT_PORT_REQUIREMENT);
		this.tradeValues.put(ResourceType.WHEAT, Utils.DEFAULT_PORT_REQUIREMENT);
		this.tradeValues.put(ResourceType.SHEEP, Utils.DEFAULT_PORT_REQUIREMENT);
		this.tradeValues.put(ResourceType.ORE, Utils.DEFAULT_PORT_REQUIREMENT);
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
	 * @return the map that defines what a player can trade with
	 */
	public Map<ResourceType, Integer> getTradeValues() {
		return this.tradeValues;
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
}
