package catan.game;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import catan.data.Player;
import catan.logic.Board;
import catan.logic.Game;

public class DistributeResourcesTests {
	@Test
	public void gameDistributeResources_CallsBoardMethod() {
		Board b = EasyMock.createMock(Board.class);
		List<Player> p = new ArrayList<Player>();
		Game g = new Game(b, p);

		// It doesn't matter what the roll is, but it should be the same one passed into
		// the distributeResources method
		int dieRoll = g.rollDie();

		b.distributeResources(p, dieRoll);
		EasyMock.expectLastCall();

		EasyMock.replay(b);

		g.distributeResources(dieRoll);

		//Assertions are if the method was called as expected.
		EasyMock.verify(b);
	}
}
