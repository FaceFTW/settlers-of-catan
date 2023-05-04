package catan.data;

import java.util.ArrayList;
import java.util.List;

import catan.Coordinate;

public class Board {
	private List<Settlement> settlements;
	private List<Road> roads;
	private List<Tile> tiles;

	public Board() {
		this.settlements = new ArrayList<>();
		this.roads = new ArrayList<>();
		this.tiles = new ArrayList<>();
	}

	public void createNewSettlement(final Coordinate location,
			final int owner) {
		settlements.add(new Settlement(location, owner, false));
	}

	public void createNewRoad(int owner, Coordinate start, Coordinate end) {
		roads.add(new Road(start, end, owner));
	}

	/*********************************
	 * Getters
	 *********************************/
	public Settlement[] getSettlements() {
		return settlements.toArray(new Settlement[settlements.size()]);
	}

	public Road[] getRoads() {
		return roads.toArray(new Road[roads.size()]);
	}

	public Tile[] getTiles() {
		return tiles.toArray(new Tile[tiles.size()]);
	}
}
