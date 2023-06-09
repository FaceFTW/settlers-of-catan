package catan.game;

import catan.data.Player;
import catan.data.ResourceType;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//CHECKSTYLE:OFF: checkstyle:magicnumber
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

    @Test
    void buildRoad_StartHasSettlementNotOwnedByPlayer_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(1, 0, 0), 2);
        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));

    }

    @Test
    void buildRoad_EndHasSettlementNotOwnedByPlayer_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, 0), 2);

        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
    }

    @Test
    void buildRoad_StartHasSettlementOwnedByPlayer_BuildsRoad() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(1, 0, 0), 1);

        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_EndHasSettlementOwnedByPlayer_BuildsRoad() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, 0), 1);

        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_StartHasRoadNotOwnedByPlayer_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(2, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
    }

    @Test
    void buildRoad_EndHasRoadNotOwnedByPlayer_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();

        Board b = new Board();
        b.createNewRoad(2, new Coordinate(2, 0, 0), new Coordinate(1, 0, 0));
        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
    }

    @Test
    void buildRoad_StartHasRoadOwnedByPlayer_BuildsRoad() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(0, 0, -1));
        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_RoadsConnectedAtEndPoints_BuildsRoad() {
        Board b = new Board();
        List<Player> player = createPlayerWithSettlementResources();
        b.createNewRoad(1, new Coordinate(2, 1, 0), new Coordinate(2, 0, 0));

        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_RoadsConnectedStartEnd_BuildsRoad() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(1, new Coordinate(2, 1, 0), new Coordinate(2, 0, 0));
        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(2, 0, 0),
                new Coordinate(1, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_EndHasRoadOwnedByPlayer_BuildsRoad() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(2, 1, 0));
        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_NoEndsConnected_OtherRoadsOwnedByPlayer_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(1, new Coordinate(0, -1, 0), new Coordinate(0, 0, -1));
        b.createNewRoad(1, new Coordinate(1, 2, 0), new Coordinate(2, 1, 0));
        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
    }

    @Test
    void buildRoad_OneEndSatisfiesConditionOtherFails_BuildsRoad() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(0, 0, -1));
        b.createNewRoad(2, new Coordinate(2, 0, 0), new Coordinate(2, 1, 0));
        Game g = new Game(b, player);

        assertTrue(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
    }

    @Test
    void buildRoad_NotConnectedToSettlement_OtherSettlementsOwned_ReturnFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(0, 0, -1), 1);

        Game g = new Game(b, player);

        assertFalse(g.buildRoad(1,
                new Coordinate(1, 0, 0),
                new Coordinate(2, 0, 0)));
    }
}
