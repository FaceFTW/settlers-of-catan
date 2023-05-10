package catan.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Road;
import catan.data.Settlement;
import catan.data.TradeOffer;

public class Game {
	public static final int MAX_DICE_VALUE = 5;
	public static final int DEFAULT_NUM_PLAYERS = 4;

	private Random random;
	private int currentTurn;
	private int numberOfPlayers;
	private List<Player> players;
	private Board board;

	public Game() {
		this.random = new Random();
		this.numberOfPlayers = DEFAULT_NUM_PLAYERS;
		this.players = new ArrayList<>();
		for (int i = 1; i <= numberOfPlayers; i++) {
			players.add(new Player(i));
		}
		this.board = new Board(random);
		this.currentTurn = 1;
	}

	// **************************************************
	// DI Constructors
	// **************************************************

	public Game(Random random) {
		this.random = random;
	}

	public Game(int numberOfPlayers, int currentTurn) {
		this.numberOfPlayers = numberOfPlayers;
		this.currentTurn = currentTurn;
	}

	public Game(Board b, List<Player> p) {
		this.board = b;
		this.players = p;
		this.random = new Random();
	}

	// **************************************************
	// Game Methods
	// **************************************************

	/**
	 * Simulates a dice roll
	 *
	 * @return the sum of two random numbers between 1 and 6
	 */
	public final int rollDie() {
		int result = random.nextInt(MAX_DICE_VALUE) + 1;
		result += random.nextInt(MAX_DICE_VALUE) + 1;
		return result;
	}

	/**
	 * Increments the current turn, fallsback to 1 if the current turn is greater
	 * than the number of players
	 */
	public void nextTurn() {
		this.currentTurn++;
		if (this.currentTurn > this.numberOfPlayers) {
			this.currentTurn = 1;
		}
	}

	/**
	 * Exchanges resources between two players
	 *
	 * @param fromPlayer
	 * @param toPlayer
	 * @param offer
	 */

	public void exchangeResources(int fromPlayer, int toPlayer, TradeOffer offer) {
		Player from = players.get(fromPlayer - 1);
		Player to = players.get(toPlayer - 1);

		from.modifyResource(ResourceType.WOOD, offer.getReceivedWood() - offer.getGivenWood());
		from.modifyResource(ResourceType.BRICK, offer.getReceivedBrick() - offer.getGivenBrick());
		from.modifyResource(ResourceType.SHEEP, offer.getReceivedSheep() - offer.getGivenSheep());
		from.modifyResource(ResourceType.WHEAT, offer.getReceivedWheat() - offer.getGivenWheat());
		from.modifyResource(ResourceType.ORE, offer.getReceivedOre() - offer.getGivenOre());

		to.modifyResource(ResourceType.WOOD, offer.getGivenWood() - offer.getReceivedWood());
		to.modifyResource(ResourceType.BRICK, offer.getGivenBrick() - offer.getReceivedBrick());
		to.modifyResource(ResourceType.SHEEP, offer.getGivenSheep() - offer.getReceivedSheep());
		to.modifyResource(ResourceType.WHEAT, offer.getGivenWheat() - offer.getReceivedWheat());
		to.modifyResource(ResourceType.ORE, offer.getGivenOre() - offer.getReceivedOre());
	}

	/**
	 * Builds a settlement if the player has the resources to do so
	 * and the location is valid
	 *
	 * @param playerId
	 * @param c
	 * @return
	 */
	public boolean buildSettlement(int playerId, Coordinate c) {
		Player p = this.players.get(playerId - 1);
		if (p.getResourceCount(ResourceType.WOOD) < 1
				|| p.getResourceCount(ResourceType.BRICK) < 1
				|| p.getResourceCount(ResourceType.SHEEP) < 1
				|| p.getResourceCount(ResourceType.WHEAT) < 1) {
			return false;
		}

		List<Settlement> settlements = this.board.getSettlements();
		List<Coordinate> settlementPositions = new ArrayList<>();

		for (Settlement s : settlements) {
			if (s.getLocation().equals(c)) {
				return false;
			}
			settlementPositions.add(s.getLocation());
		}

		List<Road> roads = this.board.getRoads();
		for (Road r : roads) {
			if (r.getOwner() == playerId && (r.getStart().equals(c) || r.getEnd().equals(c))) {
				List<Coordinate> adjacent = Arrays.asList(Utils.getBoardAdjacents(c));
				for (Coordinate adj : adjacent) {
					if (settlementPositions.contains(adj)) {
						return false;
					}

				}

				this.board.createNewSettlement(c, playerId);
				p.modifyResource(ResourceType.WOOD, -1);
				p.modifyResource(ResourceType.BRICK, -1);
				p.modifyResource(ResourceType.SHEEP, -1);
				p.modifyResource(ResourceType.WHEAT, -1);
				p.setVictoryPoints(p.getVictoryPoints() + 1);
				p.setInternalVictoryPoints(p.getInternalVictoryPoints() + 1);
				return true;
			}
		}

		return false;

	}

	/**
	 * Builds a road if the player has the resources to do so
	 * and the location is valid
	 *
	 * @param playerId
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean buildRoad(int playerId, Coordinate start, Coordinate end) {
		System.out.println("Coordinate: " + start.toString() + " " + end.toString());
		Player p = this.players.get(playerId - 1);
		if (p.getResourceCount(ResourceType.WOOD) < 1 || p.getResourceCount(ResourceType.BRICK) < 1) {
			return false;
		}

		Coordinate[] adjacents = Utils.getBoardAdjacents(start);
		if (!Arrays.asList(adjacents).contains(end)) {
			return false;
		}

		List<Settlement> settlements = this.board.getSettlements();
		List<Road> roads = this.board.getRoads();

		for (Road r : roads) {
			if (r.getStart().equals(start) && r.getEnd().equals(end)) {
				return false;
			} else if (r.getOwner() == playerId
					&& (r.getStart().equals(start) || r.getEnd().equals(start)
							|| r.getStart().equals(end) || r.getEnd().equals(end))) {
				doRoadBuild(playerId, start, end);
				return true;
			}
		}

		for (Settlement s : settlements) {
			if (s.getOwner() == playerId
					&& (s.getLocation().equals(start) || s.getLocation().equals(end))) {
				doRoadBuild(playerId, start, end);
				return true;
			}
		}

		return false;
	}

	// Reusable subroutine for buildRoad
	private void doRoadBuild(int playerId, Coordinate start, Coordinate end) {
		Player p = this.players.get(playerId - 1);
		p.modifyResource(ResourceType.WOOD, -1);
		p.modifyResource(ResourceType.BRICK, -1);
		this.board.createNewRoad(playerId, start, end);
	}

	// CHECKSTYLE:OFF: checkstyle:magicnumber
	/**
	 * Upgrades a settlement to a city if it is owned by the player and the player
	 * has the resources to do so
	 *
	 * @param playerId The ID of the player upgrading the settlement
	 * @param pos      The position of the settlement to upgrade
	 * @return True if the settlement was upgraded, false otherwise
	 */
	public boolean upgradeSettlement(int playerId, Coordinate pos) {
		Player p = this.players.get(playerId - 1);
		if (p.getResourceCount(ResourceType.ORE) < 3
				|| p.getResourceCount(ResourceType.WHEAT) < 2) {
			return false;
		}

		List<Settlement> settlements = this.board.getSettlements();
		for (Settlement settlement : settlements) {
			if (settlement.getLocation().equals(pos)) {
				if (settlement.getOwner() == playerId && !settlement.isCity()) {
					p.modifyResource(ResourceType.ORE, -3);
					p.modifyResource(ResourceType.WHEAT, -2);
					board.upgradeSettlement(pos);
					p.setVictoryPoints(p.getVictoryPoints() + 1);
					p.setInternalVictoryPoints(p.getInternalVictoryPoints() + 1);
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}
	// CHECKSTYLE:ON: checkstyle:magicnumber

	/**
	 * Distributes resources based on settlements in play and the roll
	 *
	 * @param roll the die roll that should indicate settlement payouts
	 */
	public void distributeResources(int roll) {
		board.distributeResources(this.players, roll);
	}

	// **************************************************
	// Getters and Setters
	// **************************************************

	/**
	 * @return Gets the current turn
	 */
	public int getTurn() {
		return this.currentTurn;
	}

	/**
	 * Gets the player with the given id
	 *
	 * @param playerId The ID of the player to get
	 * @return The Player object with the given ID
	 */
	public Player getPlayer(int playerId) {
		return players.get(playerId - 1);
	}

	/**
	 * Adds a player to the game
	 *
	 * @param p The Player Object to Add
	 */
	public void addPlayer(Player p) {
		this.players.add(p);
	}

	/**
	 * Gets the board object
	 *
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}

}
