package catan.data;

import catan.logic.Coordinate;

/**
 * @brief A data class representing a road on the board.
 */
public class Road {
	private final Coordinate roadStart;
	private final Coordinate roadEnd;
	private final int roadOwner;

	/**
	 * @param start The starting coordinate of the road.
	 * @param end   The ending coordinate of the road.
	 * @param owner The player who owns the road.
	 * @brief Creates a new road from the given start and end points, owned by the
	 *        specified
	 *        player.
	 */
	public Road(final Coordinate start,
			final Coordinate end,
			final int owner) {
		roadStart = start;
		roadEnd = end;
		roadOwner = owner;
	}

	/**
	 * @return The starting coordinate of the road.
	 * @brief Gets the starting coordinate of the road.
	 */
	public Coordinate getStart() {
		return roadStart;
	}

	/**
	 * @return The ending coordinate of the road.
	 * @brief Gets the ending coordinate of the road.
	 */
	public Coordinate getEnd() {
		return roadEnd;
	}

	/**
	 * @return The player who owns the road.
	 * @brief Gets the player who owns the road.
	 */
	public int getOwner() {
		return roadOwner;
	}

	/**
	 * @return A string representation of the road.
	 */
	@Override
	public String toString() {
		return "Road [roadStart=" + roadStart + ", roadEnd=" + roadEnd + ", roadOwner=" + roadOwner + "]";
	}
}
