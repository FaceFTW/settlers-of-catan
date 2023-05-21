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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void buildSettlement_ConnectedToARoadOwnedByPlayer_IsAdjacentToASettlement_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, 0),
                player.get(0).getPlayerId());
        b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c, false));
    }

    @Test
    void buildSettlement_ConnectedToARoadOwnedByPlayer_IsNotAdjacentToASettlement_BuildsSettlement() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, -1),
                player.get(0).getPlayerId());
        b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
        b.createNewRoad(1,new Coordinate(2, 0, 0), new Coordinate(2, 0, -1));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WHEAT));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.SHEEP));
        assertEquals(1, player.get(0).getVictoryPoints());
    }

    @Test
    void buildSettlement_ConnectedToARoadOwnedByPlayer_IsNotAdjacentToASettlement_RoadEnd_BuildsSettlement() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, -1),
                player.get(0).getPlayerId());
        b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(1, 0, 0));
        b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(2, 0, -1));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WHEAT));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.SHEEP));
        assertEquals(1, player.get(0).getVictoryPoints());
    }

    @Test
    void buildSettlement_NotConnectedToARoadOwnedByPlayer_OtherRoadsAreOwned_ReturnsFalse() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, -1),
                player.get(0).getPlayerId());
        b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(2, 0, -1));
        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c, false));
    }

    @Test
    void buildSettlement_ConnectedToMultipleRoadsWhereOneIsOwned_BuildsSettlement() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();
        b.createNewSettlement(new Coordinate(2, 0, -1),
                player.get(0).getPlayerId());
        b.createNewRoad(1, new Coordinate(1, 0, 0), new Coordinate(2, 0, 0));
        b.createNewRoad(1, new Coordinate(2, 0, 0), new Coordinate(2, 0, -1));
        b.createNewRoad(0, new Coordinate(1, 0, 0), new Coordinate(-1, 0, 0));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);

        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.BRICK));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WOOD));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.WHEAT));
        assertEquals(0, player.get(0).getResourceCount(ResourceType.SHEEP));
        assertEquals(1, player.get(0).getVictoryPoints());
    }

    @Test
    void buildSettlement_onTwoToOneWheatPort_playerHasImprovedWheatTrade() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();

        b.createNewRoad(1, new Coordinate(1, 0, -2), new Coordinate(1, 0, -3));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, -3);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));

        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.WHEAT));
    }

    @Test
    void buildSettlement_onTwoToOneOrePort_playerHasImprovedOreTrade() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();

        b.createNewRoad(1, new Coordinate(2, 0, -1), new Coordinate(3, 0, -1));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(3, 0, -1);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));

        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.ORE));
    }
}
