package catan.game;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Tile;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Game;
import catan.logic.Utils;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class initialPlacementDistributionTests {

    private Tile[] createPredictableTileSet() {
        Tile[] tileArray = new Tile[Utils.TILES_SPIRAL_LOCATION.length];
        for (int i = 0;i < Utils.TILES_SPIRAL_LOCATION.length;i ++) {
            Coordinate[] corners = Utils.getAdjacent(Utils.TILES_SPIRAL_LOCATION[i]);
            for (int j = 0; j < corners.length;j ++) {
                corners[j] = Utils.resolveToValid(corners[j]);
            }


            tileArray[i] = new Tile(
                    Utils.TILES_SPIRAL_LOCATION[i],
                    Utils.ALL_TILES_RESOURCES[i],
                    new ArrayList<>(Arrays.asList(corners)),
                    0
                    );
        }
        return tileArray;
    }
    @Test
    public void testInitialPlacementDistribution_withThreeDifferentTiles () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(0, 0, -2));

        assertEquals(1, p.getResourceCount(ResourceType.ORE));
        assertEquals(1, p.getResourceCount(ResourceType.BRICK));
        assertEquals(1, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(0, p.getResourceCount(ResourceType.WOOD));

        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_twoSameOneDifferentTiles () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(0, 0, -1));

        assertEquals(2, p.getResourceCount(ResourceType.ORE));
        assertEquals(1, p.getResourceCount(ResourceType.BRICK));
        assertEquals(0, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(0, p.getResourceCount(ResourceType.WOOD));
        assertEquals(0, p.getResourceCount(ResourceType.SHEEP));


        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_threeOfTheSameTileType () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(0, -1, 0));

        assertEquals(3, p.getResourceCount(ResourceType.ORE));
        assertEquals(0, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(0, p.getResourceCount(ResourceType.WOOD));
        assertEquals(0, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.BRICK));

        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_twoResourceTilesAndOneDesert () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(-1, 0, 2));

        assertEquals(1, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(1, p.getResourceCount(ResourceType.WOOD));
        assertEquals(0, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.ORE));
        assertEquals(0, p.getResourceCount(ResourceType.BRICK));

        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_twoDifferentResourceTiles () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(3, 1, 0));

        assertEquals(0, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(1, p.getResourceCount(ResourceType.WOOD));
        assertEquals(1, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.ORE));
        assertEquals(0, p.getResourceCount(ResourceType.BRICK));

        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_oneResourceTileAndDesertTile () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(-1, 0, 3));

        assertEquals(0, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(1, p.getResourceCount(ResourceType.WOOD));
        assertEquals(0, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.ORE));
        assertEquals(0, p.getResourceCount(ResourceType.BRICK));

        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_oneResourceTile () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(-4, 0, 0));

        assertEquals(1, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(0, p.getResourceCount(ResourceType.WOOD));
        assertEquals(0, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.ORE));
        assertEquals(0, p.getResourceCount(ResourceType.BRICK));

        EasyMock.verify(b);
    }

    @Test
    public void testInitialPlacementDistribution_onlyDesertTile () {
        List<Player> player = new ArrayList<>();
        Player p = new Player(1);
        player.add(p);

        Board b = EasyMock.createMock(Board.class);
        EasyMock.expect(b.getTiles()).andReturn(createPredictableTileSet());

        EasyMock.replay(b);

        Game g = new Game(b, player);

        g.distributeInitialPlacement(1, new Coordinate(-3, 0, 2));

        assertEquals(0, p.getResourceCount(ResourceType.WHEAT));
        assertEquals(0, p.getResourceCount(ResourceType.WOOD));
        assertEquals(0, p.getResourceCount(ResourceType.SHEEP));
        assertEquals(0, p.getResourceCount(ResourceType.ORE));
        assertEquals(0, p.getResourceCount(ResourceType.BRICK));

        EasyMock.verify(b);
    }

}
