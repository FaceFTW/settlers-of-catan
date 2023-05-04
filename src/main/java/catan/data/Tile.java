package catan.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import catan.Coordinate;

/**
 * A data class representing a tile on the board.
 * By default, the tile will not have a thief unless the die roll given is 7.
 * The only unit testable method in this class is the distributeResources
 * method.
 */
public class Tile {
	public static final int DESERT_DIE_ROLL = 7;
	private final Coordinate position;
	private final ResourceType resourceType;
	private final List<Coordinate> corners;
	private final int dieRoll;

	public Tile(Coordinate position,
			ResourceType resourceType,
			List<Coordinate> corners,
			int dieRoll) {
		this.position = position;
		this.resourceType = resourceType;
		this.corners = new ArrayList<>(corners);
		this.dieRoll = dieRoll;
	}

	/**
	 * @return The coordinate of the center of the tile
	 */
	public Coordinate getPosition() {
		return position;
	}

	/**
	 * @return The coordinates of the corners of the tile
	 */
	public List<Coordinate> getCorners() {
		return new ArrayList<>(this.corners);
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
	 * TODO Implement this method, check wiki for specifics.
	 *
	 * @param settlements N/A
	 * @return N/A
	 */
	public Map<ResourceType, Integer> distributeResources(List<Settlement> settlements) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
