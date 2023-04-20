/**
 * A data class representing a single location on the board
 */
public class Coordinate {
	public final int x;
	public final int y;
	public final int z;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
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
}
