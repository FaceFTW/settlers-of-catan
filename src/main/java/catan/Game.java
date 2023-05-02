package catan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import catan.data.Board;
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
		this.board = new Board();
	}

	/**************************************************
	 * DI Constructors
	 **************************************************/

	public Game(Random random) {
		this.random = random;
	}

	public Game(int numberOfPlayers, int currentTurn) {
		this.numberOfPlayers = numberOfPlayers;
		this.currentTurn = currentTurn;
	}

	public Game(Board b) {
		this.board = b;
	}

	public Game(Board b, List<Player> p) {
		this.board = b;
		this.players = p;
	}

	/**************************************************
	 * Game Methods
	 **************************************************/

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

	public boolean buildSettlement(int playerId, Coordinate c) {
		Player p = this.players.get(playerId - 1);
		if (p.getResourceCount(ResourceType.WOOD) < 1 || p.getResourceCount(ResourceType.BRICK) < 1
				|| p.getResourceCount(ResourceType.SHEEP) < 1 || p.getResourceCount(ResourceType.WHEAT) < 1) {
			return false;
		}

		Settlement[] settlements = this.board.getSettlements();
		List<Coordinate> settlementPositions = new ArrayList<>();

		for (Settlement s : settlements) {
			if (s.getLocation().equals(c)) {
				return false;
			}
			settlementPositions.add(s.getLocation());
		}

		Road[] roads = this.board.getRoads();
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

	/**************************************************
	 * Getters and Setters
	 **************************************************/

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

	public void addPlayer(Player p) {
		this.players.add(p);
	}

}
