package catan;

import catan.data.ResourceType;

public class Utils {

	private static final int MAX_NON_ZERO = 2;
	private static final int NON_ZERO_OVER = 3;
	private static final Coordinate[] REAL_LIST = new Coordinate[]{
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

	static final ResourceType[] ALL_TILES_RESOURCES = {
			ResourceType.DESERT,
			ResourceType.WOOD,
			ResourceType.WOOD,
			ResourceType.WOOD,
			ResourceType.WOOD,
			ResourceType.SHEEP,
			ResourceType.SHEEP,
			ResourceType.SHEEP,
			ResourceType.SHEEP,
			ResourceType.WHEAT,
			ResourceType.WHEAT,
			ResourceType.WHEAT,
			ResourceType.WHEAT,
			ResourceType.BRICK,
			ResourceType.BRICK,
			ResourceType.BRICK,
			ResourceType.ORE,
			ResourceType.ORE,
			ResourceType.ORE,
	};

	static final Coordinate[] TILES_SPIRAL_LOCATION = {
			new Coordinate(-2, 0, 2),
			new Coordinate(0, 0, 3),
			new Coordinate(0, 2, 2),
			new Coordinate(0, 3, 0),
			new Coordinate(2, 2, 0),
			new Coordinate(3, 0, 0),
			new Coordinate(2, 0, -2),
			new Coordinate(0, 0, -3),
			new Coordinate(0, -2, -2),
			new Coordinate(0, -3, 0),
			new Coordinate(-2, -2, 0),
			new Coordinate(-3, 0, 0),
			new Coordinate(-1, 0, 1),
			new Coordinate(0, 1, 1),
			new Coordinate(1, 1, 0),
			new Coordinate(1, 0, -1),
			new Coordinate(0, -1, -1),
			new Coordinate(-1, -1, 0),
			new Coordinate(0, 0, 0),
	};

	static final int[] TILES_ROLL_NUMBERS = {
			5,
			2,
			6,
			3,
			8,
			10,
			9,
			12,
			11,
			4,
			8,
			10,
			9,
			4,
			5,
			6,
			3,
			11
	};

	/**
	 * Empty constructor to prevent non-static usage
	 */
	protected Utils() {

	}

	//CHECKSTYLE:OFF: checkstyle:magicnumber
	public static Coordinate[] getAdjacent(Coordinate input) {
		int x = input.getX();
		int y = input.getY();
		int z = input.getZ();
		Coordinate[] returnArray = new Coordinate[6];
		returnArray[0] = new Coordinate(x - 1, y, z);
		returnArray[1] = new Coordinate(x + 1, y, z);
		returnArray[2] = new Coordinate(x, y - 1, z);
		returnArray[3] = new Coordinate(x, y + 1, z);
		returnArray[4] = new Coordinate(x, y, z - 1);
		returnArray[5] = new Coordinate(x, y, z + 1);

		return returnArray;

	}
	//CHECKSTYLE:ON: checkstyle:magicnumber

	public static Coordinate resolveToValid(Coordinate input) {
		int x = input.getX();
		int y = input.getY();
		int z = input.getZ();

		int numZero = 0;
		if (x != 0) {
			numZero++;
		}
		if (y != 0) {
			numZero++;
		}
		if (z != 0) {
			numZero++;
		}

		if (numZero < MAX_NON_ZERO) {
			return new Coordinate(x, y, z);
		}

		if (numZero == NON_ZERO_OVER || (x * y < 0 ^ x * z > 0)) {
			int factor = Integer.signum(x);
			for (; x != 0; x -= factor) {
				y += factor;
				z -= factor;
			}
		}

		if (y * z < 0) {
			int factor = Integer.signum(y);
			for (; y != 0; y -= factor) {
				x += factor;
				z += factor;
			}
		}

		return new Coordinate(x, y, z);
	}

	public static boolean isRealCoordinate(Coordinate input) {
		for (Coordinate coordinate : REAL_LIST) {
			if (coordinate.equals(input)) {
				return true;
			}
		}
		return false;
	}
}
