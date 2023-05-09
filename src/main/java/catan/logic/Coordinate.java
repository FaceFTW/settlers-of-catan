package catan.logic;

/**
 * A data class representing a single location on the board
 */
public final class Coordinate {
	private final int x;
	private final int y;
	private final int z;

	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the x coordinate value
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return the y coordinate value
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return the x coordinate value
	 */
	public int getZ() {
		return this.z;
	}

	/**
	 * @param o
	 * @return the comparison between this and o
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Coordinate) {
			Coordinate c = (Coordinate) o;
			return this.x == c.x && this.y == c.y && this.z == c.z;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return String.format("%d,%d,%d", this.x, this.y, this.z).hashCode();
	}

	@Override
	public String toString() {
		return String.format("(%d,%d,%d)", this.x, this.y, this.z);
	}
}
