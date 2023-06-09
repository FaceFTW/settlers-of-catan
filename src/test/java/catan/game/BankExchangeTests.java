package catan.game;

import catan.data.Player;
import catan.data.ResourceType;
import catan.logic.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//CHECKSTYLE:OFF: checkstyle:magicnumber
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

    @Test
    public void testBankExchange_canTradeSheepForWheat_returnsTrue() {
        Game game = new Game();
        Player p1 = game.getPlayer(1);
        p1.modifyResource(ResourceType.SHEEP, 4);
        assertTrue(game.doBankExchange(1, ResourceType.SHEEP, ResourceType.ORE));
        assertEquals(p1.getResourceCount(ResourceType.SHEEP), 0);
        assertEquals(p1.getResourceCount(ResourceType.ORE), 1);
    }

    @Test
    public void testBankExchange_canTradeOreWithExtraForWood_returnsTrue() {
        Game game = new Game();
        Player p2 = game.getPlayer(2);
        p2.modifyResource(ResourceType.ORE, 5);
        assertTrue(game.doBankExchange(2, ResourceType.ORE, ResourceType.WOOD));
        assertEquals(p2.getResourceCount(ResourceType.ORE), 1);
        assertEquals(p2.getResourceCount(ResourceType.WOOD), 1);
    }

    @Test void testBankExchange_triesToTradeBrickForBrick_returnsFalse() {
        Game game = new Game();
        Player p3 = game.getPlayer(3);
        p3.modifyResource(ResourceType.BRICK, 4);
        assertFalse(game.doBankExchange(3, ResourceType.BRICK, ResourceType.BRICK));
    }
}
