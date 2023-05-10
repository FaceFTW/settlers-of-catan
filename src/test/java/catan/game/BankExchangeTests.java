package catan.game;

import catan.data.ResourceType;
import catan.logic.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BankExchangeTests {
    @Test
    public void testFailingBankExchange_hasNoMaterials_returnsFalse() {
        Game game = new Game();
        assertFalse(game.doBankExchange(1, ResourceType.ORE, ResourceType.BRICK));
    }
}
