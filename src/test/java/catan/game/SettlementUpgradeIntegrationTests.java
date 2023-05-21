package catan.game;

import catan.data.Player;
import catan.data.ResourceType;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SettlementUpgradeIntegrationTests {
    private List<Player> createPlayerWithUpgradeResources() {
        Player player = new Player(1);
        player.modifyResource(ResourceType.WHEAT, 2);
        player.modifyResource(ResourceType.ORE, 3);

        List<Player> p = new ArrayList<>();
        p.add(player);

        return p;
    }

    @Test
    void upgradeSettlement_NotEnoughResources_ReturnsFalse() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1));
        Board b = new Board();
        Game game = new Game(b, players);

        assertFalse(game.upgradeSettlement(1, new Coordinate(1, 0, 0)));
    }
}
