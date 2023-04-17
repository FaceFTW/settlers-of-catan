package catan.data;

import org.javatuples.Triplet;

/**
 * @brief A data class representing a road on the board.
 */
public class Road {
	private final Triplet<Integer, Integer, Integer> roadStart;
	private final Triplet<Integer, Integer, Integer> roadEnd;
	private final int roadOwner;

	/**
	 * @param start The starting coordinate of the road.
	 * @param end The ending coordinate of the road.
	 * @param owner The player who owns the road.
	 * @brief Creates a new road from the given start and end points, owned by the specified
	 * player.
	 */
	public Road(final Triplet<Integer, Integer, Integer> start,
	            final Triplet<Integer, Integer, Integer> end,
	            final int owner) {
		roadStart = new Triplet<>(start.getValue0(), start.getValue1(), start.getValue2());
		roadEnd = new Triplet<>(end.getValue0(), end.getValue1(), end.getValue2());
		roadOwner = owner;
	}

	/**
	 * @return The starting coordinate of the road.
	 * @brief Gets the starting coordinate of the road.
	 */
	public Triplet<Integer, Integer, Integer> getStart() {
		return new Triplet<>(roadStart.getValue0(),
		                     roadStart.getValue1(),
		                     roadStart.getValue2());
	}

	/**
	 * @return The ending coordinate of the road.
	 * @brief Gets the ending coordinate of the road.
	 */
	public Triplet<Integer, Integer, Integer> getEnd() {
		return new Triplet<>(roadEnd.getValue0(),
		                     roadEnd.getValue1(),
		                     roadEnd.getValue2());
	}

	/**
	 * @return The player who owns the road.
	 * @brief Gets the player who owns the road.
	 */
	public int getOwner() {
		return roadOwner;
	}
}
