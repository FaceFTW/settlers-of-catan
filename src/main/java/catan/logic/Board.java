package catan.logic;

import catan.data.Tile;
import catan.data.Settlement;
import catan.data.Road;
import catan.data.ResourceType;
import catan.data.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board {
    private Tile[] tileList;
    private List<Settlement> settlements;
    private List<Road> roads;

    private Coordinate thiefPosition;

    private static final int THIEF_ROLL = 7;
    private static final int MINIMUM_LONGEST_ROAD_LENGTH = 5;

    private int longestRoadOwnerID;

    private Map<Integer, Integer> playerLongestRoads;

    public Board(Random random) {

        this.settlements = new ArrayList<>();
        this.roads = new ArrayList<>();
        this.tileList = new Tile[Utils.TILES_SPIRAL_LOCATION.length];
        this.longestRoadOwnerID = -1;

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
        this.longestRoadOwnerID = -1;
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
     *
     * @param thiefPosition
     */
    public void setThiefPosition(Coordinate thiefPosition) {
        this.thiefPosition = thiefPosition;
    }

    /**
     * returns the current thief position
     *
     * @return
     */
    public Coordinate getThiefPosition() {
        return new Coordinate(
                this.thiefPosition.getX(),
                this.thiefPosition.getY(),
                this.thiefPosition.getZ());
    }

    /**
     * @param players, a list of players in the game
     * @param roll,    a integer die roll
     */
    public void distributeResources(List<Player> players, int roll) {
        if (roll == THIEF_ROLL) {
            return;
        }

        for (Tile t : tileList) {
            Coordinate tileCenter = t.getPosition();
            if (tileCenter.equals(this.thiefPosition)) {
                continue;
            }

            if (t.getDieRoll() == roll) {

                Coordinate[] allAdjacent = Utils.getAdjacent(tileCenter);
                for (int i = 0; i < allAdjacent.length; i++) {
                    allAdjacent[i] = Utils.resolveToValid(allAdjacent[i]);

                    for (Settlement s : settlements) {
                        if (s.getLocation().equals(allAdjacent[i])) {
                            for (Player p : players) {
                                if (p.getPlayerId() == s.getOwner()) {
                                    if (s.isCity()) {
                                        p.modifyResource(
                                                t.getResourceType(),
                                                2);
                                    } else {
                                        p.modifyResource(
                                                t.getResourceType(),
                                                1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the which player owns the longest road,
     * should be called after every road placement and settlement placement.
     */
    public void updateLongestRoad() {
        int longest;
        this.playerLongestRoads = new HashMap<>();

        for (Road r : roads) {
            ArrayList<Road> originalList = new ArrayList<>();
            originalList.add(r);

            int lengthFromEnd = getLengthPossible(r.getOwner(), r, r.getEnd(), new ArrayList<>(originalList));
            int lengthFromStart = getLengthPossible(r.getOwner(), r, r.getStart(), new ArrayList<>(originalList));
            int greaterLength = Math.max(lengthFromStart, lengthFromEnd);
            int pc = r.getOwner();
            longest = this.playerLongestRoads.getOrDefault(pc, 0);

            this.playerLongestRoads.put(pc, Math.max(greaterLength, longest));
        }

        longest = this.playerLongestRoads.getOrDefault(this.longestRoadOwnerID, 0);
        int pid = this.longestRoadOwnerID;

        for (Map.Entry<Integer, Integer> k : this.playerLongestRoads.entrySet()) {
            if (k.getValue() > longest) {
                longest = k.getValue();
                if (longest >= MINIMUM_LONGEST_ROAD_LENGTH) {
                    pid = k.getKey();
                }
            }
        }

        this.longestRoadOwnerID = pid;
    }

    private int getLengthPossible(int playerID, Road r, Coordinate c, List<Road> usedRoads) {
        int thisRoadsLongest = 1;
        for (Road nr : roads) {
            if (nr.getOwner() != playerID) {
                continue;
            }

            // Check for equality
            if (roadsEqual(r, nr)) {
                continue;
            }

            // Check if it's been used already
            boolean used = false;
            for (Road or : usedRoads) {
                if (roadsEqual(or, nr)) {
                    used = true;
                }
            }

            for (Settlement s : settlements) {
                if (s.getLocation().equals(c) && s.getOwner() != playerID) {
                    used = true;
                }
            }

            if (used) {
                continue;
            }

            // Get the new direction pointer
            Coordinate newEnd;
            if (nr.getStart().equals(c)) {
                newEnd = nr.getEnd();
            } else if (nr.getEnd().equals(c)) {
                newEnd = nr.getStart();
            } else {
                continue;
            }

            // get new longest and return
            ArrayList<Road> newList = new ArrayList<>(usedRoads);
            newList.add(nr);
            int possibleLongest = 1 + getLengthPossible(playerID, nr, newEnd, newList);
            thisRoadsLongest = Math.max(thisRoadsLongest, possibleLongest);
        }
        return thisRoadsLongest;
    }

    private boolean roadsEqual(Road r1, Road r2) {
        return r1.getStart() == r2.getStart() && r1.getEnd() == r2.getEnd();
        // I think it's worth noting that although the swapped values above should be considered equal,
        // but because we only iterate over existing roads in the implementation there is no case where the other
        // road could exist.
    }

    /**
     * Returns the id of the current longest road
     *
     * @return
     */
    public int getLongestRoadOwnerID() {
        return longestRoadOwnerID;
    }


}
