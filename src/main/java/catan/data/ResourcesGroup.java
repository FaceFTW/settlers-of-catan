package catan.data;

/**
 * Represents a set number of resources, used for trading
 */
public final class ResourcesGroup {
	private final int wood;
	private final int brick;
	private final int sheep;
	private final int wheat;
	private final int ore;

	public ResourcesGroup(int wood, int brick, int sheep, int wheat, int ore) {
		this.wood = wood;
		this.brick = brick;
		this.sheep = sheep;
		this.wheat = wheat;
		this.ore = ore;
	}

	public int wood() {
		return wood;
	}

	public int brick() {
		return brick;
	}

	public int sheep() {
		return sheep;
	}

	public int wheat() {
		return wheat;
	}

	public int ore() {
		return ore;
	}
}
