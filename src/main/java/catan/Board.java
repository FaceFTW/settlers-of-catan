package catan;

import catan.data.ResourceType;
import catan.data.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
	public Tile[] tileList;

	public Board(Random random) {
		this.tileList = new Tile[19];

		List<ResourceType> resources = new ArrayList<>(
				Arrays.asList(Utils.ALL_TILES_RESOURCES));

		int desertEffect = 0;
		for (int i = 0; i < 19; i++) {
			// Tile Coordinate
			Coordinate coordinate = Utils.TILES_SPIRAL_LOCATION[i];

			// Tile resource
			ResourceType resource = resources.remove(random.nextInt(resources.size()));

			// Tile corners
			ArrayList<Coordinate> corners = new ArrayList<>();
			Coordinate[] possibleCorners = Utils.getAdjacent(coordinate);
			for (int j = 0; j < 6; j++) {
				possibleCorners[j] = Utils.resolveToValid(possibleCorners[j]);
				if (Utils.isRealCoordinate(possibleCorners[j])) {
					corners.add(possibleCorners[j]);
				}
			}

			// Tile die roll
			int dieRoll;
			if (resource == ResourceType.DESERT) {
				dieRoll = 7;
				desertEffect = -1;
			} else {
				dieRoll = Utils.TILES_ROLL_NUMBERS[i + desertEffect];
			}

			// Tile creation
			Tile t = new Tile(coordinate, resource, corners, dieRoll);
			tileList[i] = t;
		}
	}
}
