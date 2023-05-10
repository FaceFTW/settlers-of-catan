package catan.game;

import catan.data.ResourceType;
import catan.logic.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BankExchangeTests {
    @Test
    public void testBankExchange_hasNoMaterials_returnsFalse() {
        Game game = new Game();
        assertFalse(game.doBankExchange(1, ResourceType.ORE, ResourceType.BRICK));
    }

    @Test
    public void testBankExchange_hasInsufficientMaterials_returnsFalse() {
        Game game = new Game();
        game.getPlayer(1).modifyResource(ResourceType.WOOD, 3);
        assertFalse(game.doBankExchange(1, ResourceType.WOOD, ResourceType.WHEAT));
    }
}
