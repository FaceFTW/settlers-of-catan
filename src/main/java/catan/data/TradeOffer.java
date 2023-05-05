package catan.data;

public final class TradeOffer {
	private final ResourcesGroup givenResources;
	private final ResourcesGroup receivedResources;

	public TradeOffer(ResourcesGroup givenResources, ResourcesGroup receivedResources) {
		this.givenResources = givenResources;
		this.receivedResources = receivedResources;
	}

	public int getGivenWood() {
		return givenResources.wood();
	}

	public int getGivenBrick() {
		return givenResources.brick();
	}

	public int getGivenSheep() {
		return givenResources.sheep();
	}

	public int getGivenWheat() {
		return givenResources.wheat();
	}

	public int getGivenOre() {
		return givenResources.ore();
	}

	public int getReceivedWood() {
		return receivedResources.wood();
	}

	public int getReceivedBrick() {
		return receivedResources.brick();
	}

	public int getReceivedSheep() {
		return receivedResources.sheep();
	}

	public int getReceivedWheat() {
		return receivedResources.wheat();
	}

	public int getReceivedOre() {
		return receivedResources.ore();
	}

}
