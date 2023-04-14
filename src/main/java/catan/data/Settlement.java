package catan.data;

import org.javatuples.Triplet;

/**
 * @brief A data class representing a settlement on the board.
 */
public class Settlement {
	private final Triplet<Integer, Integer, Integer> settlementLocation;
	private final int setlementOwner;
	private boolean isCity;

	/**
	 * @param location
	 * @param owner
	 * @param city
	 * @brief Creates a new settlement at the given location, owned by the specified player.
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
	 * @brief Gets the location of the settlement.
	 */
	public Triplet<Integer, Integer, Integer> getLocation() {
		return settlementLocation;
	}

	/**
	 * @return The player who owns the settlement.
	 * @brief Gets the player who owns the settlement.
	 */
	public int getOwner() {
		return setlementOwner;
	}

	/**
	 * @return True if the settlement is a city, false otherwise.
	 * @brief Checks if the settlement is a city.
	 */
	public boolean isCity() {
		return isCity;
	}

	/**
	 * @brief Upgrades the settlement to a city. You cannot reverse this action by intention
	 */
	public void upgradeToCity() {
		isCity = true;
	}

}
