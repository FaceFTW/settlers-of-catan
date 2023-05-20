package catan.game;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Road;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RoadBuildIntegrationTests {
    private List<Player> createPlayerWithSettlementResources() {
        Player player = new Player(1);
        player.modifyResource(ResourceType.BRICK, 1);
        player.modifyResource(ResourceType.WOOD, 1);

        List<Player> p = new ArrayList<>();
        p.add(player);

        return p;
    }
    @Test
    void buildRoad_BothStartEndOccupiedByRoad_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
        b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(1, 0, 0));
        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
    }
}
