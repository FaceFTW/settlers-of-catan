package catan.data;

import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;

/**
 * A data class representing a tile on the board.
 * By default, the tile will not have a thief unless the die roll given is 7.
 * The only unit testable method in this class is the distributeResources method.
 */
public class Tile {
	public static final int DESERT_DIE_ROLL = 7;
	private final Triplet<Integer, Integer, Integer> position;
	private final ResourceType resourceType;
	private final List<Triplet<Integer, Integer, Integer>> corners;
	private boolean hasThief;
	private final int dieRoll;

	public Tile(Triplet<Integer, Integer, Integer> position,
				ResourceType resourceType,
				List<Triplet<Integer, Integer, Integer>> corners,
				int dieRoll) {
		this.position = position;
		this.resourceType = resourceType;
		this.corners = corners;
		this.hasThief = dieRoll == DESERT_DIE_ROLL;
		this.dieRoll = dieRoll;
	}

	/**
	 * @return The coordinate of the center of the tile
	 */
	public Triplet<Integer, Integer, Integer> getPosition() {
		return position;
	}

	/**
	 * @return The type of resource this tile has
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * @return The die roll for this tile
	 */
	public int getDieRoll() {
		return dieRoll;
	}

	/**
	 * @return Returns if this tile has a thief on it
	 */
	public boolean hasThief() {
		return hasThief;
	}

	/**
	 * Clears or sets the thief on this tile
	 *
	 * @param thief If true, the thief will be set on this tile. If false, the thief will be
	 * cleared from this tile.
	 */
	public void setThief(boolean thief) {
		this.hasThief = thief;
	}

	/**
	 * TODO Implement this method, check wiki for specifics.
	 *
	 * @param settlements N/A
	 * @return N/A
	 */
	public Map<ResourceType, Integer> distributeResources(List<Settlement> settlements) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
