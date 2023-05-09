package catan.data;

import catan.Coordinate;

/**
 * A data class representing a settlement on the board.
 */
public class Settlement {
	private final Coordinate settlementLocation;
	private final int setlementOwner;
	private boolean isCity;

	/**
	 * Creates a new settlement at the given location, owned by the specified
	 * player.
	 *
	 * @param location
	 * @param owner
	 * @param city
	 */
	public Settlement(final Coordinate location,
			final int owner,
			final boolean city) {
		settlementLocation = location;
		setlementOwner = owner;
		isCity = city;
	}

	/**
	 * @return The location of the settlement.
	 */
	public Coordinate getLocation() {
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
	 * Upgrades the settlement to a city. You cannot reverse this action by
	 * intention
	 */
	public void upgradeToCity() {
		this.isCity = true;
	}

	/**
	 * @return A string representation of the settlement.
	 */
	@Override
	public String toString() {
		return "Settlement:[" + settlementLocation.toString()
				+ ", Owner: " + setlementOwner + ", IsCity: "
				+ isCity + "]";
	}

}
