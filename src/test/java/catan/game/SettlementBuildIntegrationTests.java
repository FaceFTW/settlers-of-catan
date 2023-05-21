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

    @Test
    void buildSettlement_onTwoToOneOrePort_playerHasImprovedSheepTrade() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();

        b.createNewRoad(1, new Coordinate(0, 2, 1), new Coordinate(0, 3, 1));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(0, 3, 1);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));

        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.SHEEP));
    }

    @Test
    void buildSettlement_onTwoToOneOrePort_playerHasImprovedBrickTrade() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();

        b.createNewRoad(1, new Coordinate(-2, -1, 0), new Coordinate(-3, -1, 0));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(-3, -1, 0);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));

        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.BRICK));
    }

    @Test
    void buildSettlement_onTwoToOneOrePort_playerHasImprovedWoodTrade() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();

        b.createNewRoad(1, new Coordinate(-1, -2, 0), new Coordinate(-1, -3, 0));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(-1, -3, 0);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));

        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.WOOD));
    }

    @Test
    void buildSettlement_onThreeTwoOnePort_playerHasImprovedTrades() {
        List<Player> player = createPlayerWithSettlementResources();
        Board b = new Board();

        b.createNewRoad(1, new Coordinate(3, 1, 0), new Coordinate(3, 2, 0));

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(3, 2, 0);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c, false));

        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WOOD));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.BRICK));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.ORE));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.SHEEP));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WHEAT));
    }

    @Test
    void buildSettlement_3To1ClaimedBefore2To1_playerHasImprovedTrades() {
        List<Player> player = createPlayerWithSettlementResources();

        player.get(0).modifyResource(ResourceType.SHEEP, 1);
        player.get(0).modifyResource(ResourceType.WHEAT, 1);
        player.get(0).modifyResource(ResourceType.BRICK, 1);
        player.get(0).modifyResource(ResourceType.WOOD, 1);

        Board b = new Board();

        b.createNewRoad(1, new Coordinate(3, 1, 0), new Coordinate(3, 2, 0));
        b.createNewRoad(1, new Coordinate(2, 0, -1), new Coordinate(3, 0, -1));

        Game game = new Game(b, player);

        Coordinate c1 = new Coordinate(3, 2, 0);
        Coordinate c2 = new Coordinate(3, 0, -1);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c1, false));
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c2, false));

        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WOOD));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.BRICK));
        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.ORE));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.SHEEP));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WHEAT));
    }

    @Test
    void buildSettlement_3To1ClaimedAfter2To1_playerHasImprovedTrades() {
        List<Player> player = createPlayerWithSettlementResources();

        player.get(0).modifyResource(ResourceType.SHEEP, 1);
        player.get(0).modifyResource(ResourceType.WHEAT, 1);
        player.get(0).modifyResource(ResourceType.BRICK, 1);
        player.get(0).modifyResource(ResourceType.WOOD, 1);

        Board b = new Board();

        b.createNewRoad(1, new Coordinate(3, 1, 0), new Coordinate(3, 2, 0));
        b.createNewRoad(1, new Coordinate(2, 0, -1), new Coordinate(3, 0, -1));

        Game game = new Game(b, player);

        Coordinate c1 = new Coordinate(3, 2, 0);
        Coordinate c2 = new Coordinate(3, 0, -1);
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c2, false));
        assertTrue(game.buildSettlement(player.get(0).getPlayerId(), c1, false));

        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WOOD));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.BRICK));
        assertEquals(2, player.get(0).getTradeValues().get(ResourceType.ORE));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.SHEEP));
        assertEquals(3, player.get(0).getTradeValues().get(ResourceType.WHEAT));
    }

    @Test
    void buildSettlement_InitialBuilding_AdjacentSettlement_StillFails() {
        List<Player> player = createPlayerWithSettlementResources();

        Board b = new Board();

        b.createNewSettlement(new Coordinate(2, 0, 0), 1);

        Game game = new Game(b, player);

        Coordinate c = new Coordinate(1, 0, 0);
        assertFalse(game.buildSettlement(player.get(0).getPlayerId(), c, true));
    }


}
