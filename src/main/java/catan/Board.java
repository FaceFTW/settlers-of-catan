package catan;

import catan.data.ResourceType;
import catan.data.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
	private Tile[] tileList;

	private static final int THIEF_ROLL = 4;

	public Board(Random random) {
		this.tileList = new Tile[Utils.TILES_SPIRAL_LOCATION.length];

		List<ResourceType> resources = new ArrayList<>(
				Arrays.asList(Utils.ALL_TILES_RESOURCES));

		int desertEffect = 0;
		for (int i = 0; i < Utils.TILES_SPIRAL_LOCATION.length; i++) {
			// Tile Coordinate
			Coordinate coordinate = Utils.TILES_SPIRAL_LOCATION[i];

			// Tile resource
			ResourceType resource = resources.remove(random.nextInt(resources.size()));

			// Tile corners
			ArrayList<Coordinate> corners = new ArrayList<>();
			Coordinate[] possibleCorners = Utils.getAdjacent(coordinate);
			for (int j = 0; j < possibleCorners.length; j++) {
				possibleCorners[j] = Utils.resolveToValid(possibleCorners[j]);
				if (Utils.isRealCoordinate(possibleCorners[j])) {
					corners.add(possibleCorners[j]);
				}
			}

			// Tile die roll
			int dieRoll;
			if (resource == ResourceType.DESERT) {
				dieRoll = THIEF_ROLL;
				desertEffect = -1;
			} else {
				dieRoll = Utils.TILES_ROLL_NUMBERS[i + desertEffect];
			}

			// Tile creation
			Tile t = new Tile(coordinate, resource, corners, dieRoll);
			tileList[i] = t;
		}
	}

	/**
	 * Returns all the tiles
	 *
	 * @return
	 */
	public Tile[] getTiles() {
		return this.tileList;
	}
}
