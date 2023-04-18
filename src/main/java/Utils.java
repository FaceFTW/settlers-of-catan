import org.javatuples.Triplet;

public class Utils {
	private static Coordinate[] realList = new Coordinate[] {
			new Coordinate(2, 0, -3),
			new Coordinate(3, 0, -2),

			new Coordinate(0, 0, -4),
			new Coordinate(1, 0, -3),
			new Coordinate(2, 0, -2),
			new Coordinate(3, 0, -2),
			new Coordinate(4, 0, 0),

			new Coordinate(0, -2, -3),
			new Coordinate(0, -1, -3),
			new Coordinate(0, 0, -3),
			new Coordinate(1, 0, -2),
			new Coordinate(2, 0, -1),
			new Coordinate(3, 0, 0),
			new Coordinate(3, 1, 0),
			new Coordinate(3, 2, 0),

			new Coordinate(0, -3, -2),
			new Coordinate(-2, -2, 0),
			new Coordinate(0, -1, -2),
			new Coordinate(0, 0, -2),
			new Coordinate(1, 0, -1),
			new Coordinate(2, 0, 0),
			new Coordinate(2, 1, 0),
			new Coordinate(2, 2, 0),
			new Coordinate(2, 3, 0),

			new Coordinate(0, -3, -1),
			new Coordinate(0, -2, -1),
			new Coordinate(0, -1, -1),
			new Coordinate(0, 0, -1),
			new Coordinate(1, 0, 0),
			new Coordinate(1, 1, 0),
			new Coordinate(1, 2, 0),
			new Coordinate(1, 3, 0),

			new Coordinate(0, -4, 0),
			new Coordinate(0, -3, 0),
			new Coordinate(0, -2, 0),
			new Coordinate(0, -1, 0),
			new Coordinate(0, 0, 0),
			new Coordinate(0, 1, 0),
			new Coordinate(0, 2, 0),
			new Coordinate(0, 3, 0),
			new Coordinate(0, 4, 0),

			new Coordinate(-1, -3, 0),
			new Coordinate(-1, -2, 0),
			new Coordinate(-1, -1, 0),
			new Coordinate(-1, 0, 0),
			new Coordinate(0, 0, 1),
			new Coordinate(0, 1, 1),
			new Coordinate(0, 2, 1),
			new Coordinate(0, 3, 1),

			new Coordinate(-2, -3, 0),
			new Coordinate(-2, -2, 0),
			new Coordinate(-2, -1, 0),
			new Coordinate(-2, 0, 0),
			new Coordinate(-1, 0, 1),
			new Coordinate(0, 0, 2),
			new Coordinate(0, 1, 2),
			new Coordinate(0, 2, 2),
			new Coordinate(0, 3, 2),

			new Coordinate(-3, -2, 0),
			new Coordinate(-3, -1, 0),
			new Coordinate(-3, 0, 0),
			new Coordinate(-2, 0, 1),
			new Coordinate(-1, 0, 2),
			new Coordinate(0, 0, 3),
			new Coordinate(0, 1, 3),
			new Coordinate(0, 2, 3),

			new Coordinate(-4, 0, 0),
			new Coordinate(-3, 0, 1),
			new Coordinate(-2, 0, 2),
			new Coordinate(-1, 0, 3),
			new Coordinate(0, 0, 4),

			new Coordinate(-3, 0, 2),
			new Coordinate(-2, 0, 3),
	};
	public static Coordinate[] getAdjacent(Coordinate input) {
		int x = input.x;
		int y = input.y;
		int z = input.z;

		Coordinate[] returnArray = new Coordinate[6];
		returnArray[0] = new Coordinate(x - 1, y, z);
		returnArray[1] = new Coordinate(x + 1, y, z);
		returnArray[2] = new Coordinate(x, y - 1, z);
		returnArray[3] = new Coordinate(x, y + 1, z);
		returnArray[4] = new Coordinate(x, y, z - 1);
		returnArray[5] = new Coordinate(x, y, z + 1);

		return returnArray;
	}
	public static Coordinate resolveToValid(Coordinate input) {
		int x = input.x;
		int y = input.y;
		int z = input.z;

		if (x != 0 && y != 0 && z != 0) {
			if (x > 0) {
				for (;x > 0;x --) {
					y ++;
					z --;
				}
			} else {
				for (;x < 0;x ++) {
					y --;
					z ++;
				}
			}
		}

		if (x * y < 0 || x * z > 0) {
			if (x > 0) {
				for (;x > 0;x --) {
					y ++;
					z --;
				}
			} else {
				for (;x < 0;x ++) {
					y --;
					z ++;
				}
			}
		} else if (y * z < 0) {
			if (y > 0) {
				for (;y > 0;y --) {
					x ++;
					z ++;
				}
			} else {
				for (;y < 0;y ++) {
					x --;
					z --;
				}
			}
		}

		return new Coordinate(x, y, z);
	}

	public static boolean isRealCoordinate(Coordinate input) {
		throw new UnsupportedOperationException();
	}
}
