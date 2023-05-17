package catan.logic;

import catan.data.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utils {

	private static final int MAX_NON_ZERO = 2;
	private static final int NON_ZERO_OVER = 3;
	public static final Coordinate[] REAL_LIST = new Coordinate[]{
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

	public static final ResourceType[] ALL_TILES_RESOURCES = {
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


	public static final Coordinate[] TILES_SPIRAL_LOCATION = {
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

	// CHECKSTYLE:OFF: checkstyle:magicnumber
	public static final HashMap<Coordinate, ResourceType> PORT_MAP = new HashMap<>();
	static {
		PORT_MAP.put(new Coordinate(0, 0, -4), ResourceType.WHEAT);
		PORT_MAP.put(new Coordinate(1, 0, -3), ResourceType.WHEAT);
		PORT_MAP.put(new Coordinate(3, 0, -1), ResourceType.ORE);
		PORT_MAP.put(new Coordinate(4, 0, 0), ResourceType.ORE);
		PORT_MAP.put(new Coordinate(3, 2, 0), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(2, 3, 0), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(0, 4, 0), ResourceType.SHEEP);
		PORT_MAP.put(new Coordinate(0, 3, 1), ResourceType.SHEEP);
		PORT_MAP.put(new Coordinate(0, 1, 3), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(0, 0, 4), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(-3, 0, 2), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(-2, 0, 3), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(-3, -1, 0), ResourceType.BRICK);
		PORT_MAP.put(new Coordinate(-4, 0, 0), ResourceType.BRICK);
		PORT_MAP.put(new Coordinate(0, -4, 0), ResourceType.WOOD);
		PORT_MAP.put(new Coordinate(-1, -3, 0), ResourceType.WOOD);
		PORT_MAP.put(new Coordinate(0, -2, -3), ResourceType.DESERT);
		PORT_MAP.put(new Coordinate(0, -3, -2), ResourceType.DESERT);
	}
	// CHECKSTYLE:ON: checkstyle:magicnumber

	public static final int THREE_TO_ONE_PORT_REQUIREMENT = 3;
	public static final int TWO_TO_ONE_PORT_REQUIREMENT = 2;
	public static final int DEFAULT_PORT_REQUIREMENT = 4;

	public static final int[] TILES_ROLL_NUMBERS = {
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

	// CHECKSTYLE:OFF: checkstyle:magicnumber
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
	// CHECKSTYLE:ON: checkstyle:magicnumber

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

		if (x * z > 0) {
			int factor = Integer.signum(z);
			for(; z != 0; z -= factor) {
				x -= factor;
				y += factor;
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

	/**
	 * Returns the adjacent coordinates of a point on the board.
	 * Generally, if the coordinate is for the center of a hex, this will return the
	 * 6 hexes around it.
	 * If the coordinate is for a vertex, this will return the 3 hexes around it.
	 *
	 * @return the coordinates that are orthogonally adjacent to the input
	 */
	public static Coordinate[] getBoardAdjacents(final Coordinate origin) {
		Coordinate[] baseAdjacents = Utils.getAdjacent(origin);
		for (int i = 0; i < baseAdjacents.length; i++) {
			baseAdjacents[i] = Utils.resolveToValid(baseAdjacents[i]);
		}
		List<Coordinate> adjacents = new ArrayList<>(Arrays.asList(baseAdjacents));
		List<Coordinate> tileCoords = new ArrayList<>(Arrays.asList(TILES_SPIRAL_LOCATION));
		adjacents.removeIf(c -> !Utils.isRealCoordinate(c) || tileCoords.contains(c));

		return adjacents.toArray(new Coordinate[adjacents.size()]);
	}
}
