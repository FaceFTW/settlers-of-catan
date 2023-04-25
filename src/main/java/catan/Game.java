package catan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.TradeOffer;

public class Game {
	public static final int MAX_DICE_VALUE = 5;
	public static final int DEFAULT_NUM_PLAYERS = 4;

	private Random random;
	private int currentTurn;
	private int numberOfPlayers;
	private List<Player> players;

	public Game() {
		this.random = new Random();
		this.numberOfPlayers = DEFAULT_NUM_PLAYERS;
		this.players = new ArrayList<>();
	}

	/**
	 * DI constructor, only injects the random object
	 *
	 * @param random
	 */
	public Game(Random random) {
		this.random = random;
	}

	/**
	 * DI constructor, only injects the number of players and the current turn
	 *
	 * @param numberOfPlayers
	 */
	public Game(int numberOfPlayers, int currentTurn) {
		this.numberOfPlayers = numberOfPlayers;
		this.currentTurn = currentTurn;
	}

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
