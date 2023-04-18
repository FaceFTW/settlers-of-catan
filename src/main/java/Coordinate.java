/**
 * A data class representing a single location on the board
 */
public class Coordinate {
	public final int x;
	public final int y;
	public final int z;

	/**
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public Coordinate (int x, int y,int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		Coordinate c = (Coordinate) obj;
		if (this.x != c.x || this.y != c.y || this.z != c.z)
			return false;
		return true;
	}
}
