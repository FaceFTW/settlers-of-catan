package catan.game;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Road;
import catan.data.Settlement;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SettlementBuildIntegrationTests {

    private List<Player> createPlayerWithSettlementResources() {
        Player player = new Player(1);
        player.modifyResource(ResourceType.SHEEP, 1);
        player.modifyResource(ResourceType.WHEAT, 1);
        player.modifyResource(ResourceType.BRICK, 1);
        player.modifyResource(ResourceType.WOOD, 1);

        List<Player> p = new ArrayList<>();
        p.add(player);

        return p;
    }
    @Test
    void buildSettlement_PosIsOccupied_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(1, 0, 0),
                player.get(0).getPlayerId());

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c, false));
    }

    @Test
    void buildSettlement_NotConnectedToARoad_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 2, 0),
                player.get(0).getPlayerId());
        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c, false));
    }

    @Test
    void buildSettlement_ConnectedToARoadNotOwned_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 2, 0),
                player.get(0).getPlayerId());
        b.createNewRoad(2, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c, false));
    }
}
