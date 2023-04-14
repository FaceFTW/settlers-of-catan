package catan.data;

import org.javatuples.Triplet;

/**
 * A data class representing a settlement on the board.
 */
public class Settlement {
	private final Triplet<Integer, Integer, Integer> settlementLocation;
	private final int setlementOwner;
	private boolean isCity;

	/**
	 * Creates a new settlement at the given location, owned by the specified player.
	 *
	 * @param location
	 * @param owner
	 * @param city
	 */
	public Settlement(final Triplet<Integer, Integer, Integer> location,
					final int owner,
					final boolean city) {
		settlementLocation = location;
		setlementOwner = owner;
		isCity = city;
	}

	/**
	 * @return The location of the settlement.
	 */
	public Triplet<Integer, Integer, Integer> getLocation() {
		return settlementLocation;
	}

	/**
	 * @return The player who owns the settlement.
	 */
	public int getOwner() {
		return setlementOwner;
	}

	/**
	 * @return True if the settlement is a city, false otherwise.
	 */
	public boolean isCity() {
		return isCity;
	}

	/**
	 * Upgrades the settlement to a city. You cannot reverse this action by intention
	 */
	public void upgradeToCity() {
		isCity = true;
	}

}
