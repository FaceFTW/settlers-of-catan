package catan;

import catan.data.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
	private Tile[] tileList;
	private List<Settlement> settlements;
	private List<Road> roads;

	Coordinate thiefPosition;

	private static final int THIEF_ROLL = 7;

	public Board(Random random) {

		this.settlements = new ArrayList<>();
		this.roads = new ArrayList<>();
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
				thiefPosition = coordinate;
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

	// Simplified Constructor used for testing
	public Board() {
		this.settlements = new ArrayList<>();
		this.roads = new ArrayList<>();
	}

	/**
	 * Creates a new settlement at the given location
	 *
	 * @param location the location of the settlement
	 * @param owner    the owner of the settlement
	 */
	public void createNewSettlement(final Coordinate location,
			final int owner) {
		settlements.add(new Settlement(location, owner, false));
	}

	/**
	 * Creates a new road between the two given coordinates
	 *
	 * @param owner the owner of the road
	 * @param start the start coordinate
	 * @param end   the end coordinate
	 */
	public void createNewRoad(int owner, Coordinate start, Coordinate end) {
		roads.add(new Road(start, end, owner));
	}

	/**
	 * Assumes the coordinate already has a settlement, and upgrades it to a city
	 *
	 * @param location
	 */
	public void upgradeSettlement(Coordinate location) {
		for (Settlement s : settlements) {
			if (s.getLocation().equals(location)) {
				s.upgradeToCity();
				return;
			}
		}
	}

	// *********************************
	// Getters
	// *********************************

	/**
	 * Returns all the settlements on the board
	 *
	 * @return
	 */
	public List<Settlement> getSettlements() {
		List<Settlement> retList = this.settlements == null
				? new ArrayList<>()
				: new ArrayList<>(settlements);
		return retList;
	}

	/**
	 * Returns all the roads on the board
	 *
	 * @return
	 */
	public List<Road> getRoads() {
		List<Road> retList = this.roads == null
				? new ArrayList<>()
				: new ArrayList<>(roads);
		return new ArrayList<>(retList);
	}

	/**
	 * Returns all the tiles
	 *
	 * @return
	 */
	public Tile[] getTiles() {
		return this.tileList.clone();
	}

	/**
	 * sets the position of the thief
	 * @param thiefPosition
	 */
	public void setThiefPosition(Coordinate thiefPosition) {
		this.thiefPosition = thiefPosition;
	}

	/**
	 * returns the current thief position
	 * @return
	 */
	public Coordinate getThiefPosition() {
		return new Coordinate(
				this.thiefPosition.getX(),
				this.thiefPosition.getY(),
				this.thiefPosition.getZ());
	}

	/**
	 *
	 * @param players, a list of players in the game
	 * @param roll, a integer die roll
	 */
	public void distributeResources(List<Player> players, int roll) {
		for (Player p: players) {
			p.modifyResource(ResourceType.WOOD, 1);
		}
	}
}
