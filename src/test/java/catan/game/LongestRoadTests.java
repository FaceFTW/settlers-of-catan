package catan.game;

import catan.logic.Board;
import catan.logic.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//CHECKSTYLE:OFF: checkstyle:magicnumber
public class LongestRoadTests {
    @Test
    public void testUpdateLongestRoad_withEmptyBoard_returnsNegativeOne() {
        Board b = new Board();
        b.updateLongestRoad();
        assertEquals(-1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withRoadFourLong_returnsNegativeOne() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0)
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(-1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withRoadFiveLong_returnsOne() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(1, 0, 0)
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withRoadFiveRoadsSplit_returnsNegativeOne() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        c = new Coordinate[]{
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
                new Coordinate(1, 0, -2),
                new Coordinate(0, 0, -2),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(-1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withRoadFiveInLineFromTwoPlayers_returnsNegativeOne() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(1, 0, 0)
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(i % 2 + 1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(-1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withComplexBranchingRoad_returnsTwo() {
        Board b = new Board();

        //Simple Road
        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
                new Coordinate(1, 0, -2),
                new Coordinate(0, 0, -2),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        //Longer branching road
        c = new Coordinate[]{
                new Coordinate(0, 3, 1),
                new Coordinate(0, 2, 1),
                new Coordinate(0, 1, 2),
                new Coordinate(0, 0, 2),
                new Coordinate(-1, 0, 2),
                new Coordinate(-2, 0, 1),
                new Coordinate(-2, 0, 0),
                new Coordinate(-1, 0, 0),
                new Coordinate(0, -1, 0),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(2, c[i - 1], c[i]);
        }
        b.createNewRoad(2, new Coordinate(0, 0, 2), new Coordinate(0, 0, 1));
        b.createNewRoad(2, new Coordinate(-1, 0, 0), new Coordinate(0, 0, 1));

        b.updateLongestRoad();
        assertEquals(2, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withTwoPlayersHavingFiveLongRoads_returnsFirstPlayer() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());

        //Longer branching road
        c = new Coordinate[]{
                new Coordinate(0, 3, 1),
                new Coordinate(0, 2, 1),
                new Coordinate(0, 1, 2),
                new Coordinate(0, 0, 2),
                new Coordinate(-1, 0, 2),
                new Coordinate(-2, 0, 1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(2, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withTwoPlayersHavingFiveLongRoadsWhiteBox_returnsFirstPlayer() {
        Board b = new Board();

        b.createNewRoad(2, new Coordinate(0, 3, 1), new Coordinate(0, 2, 1));

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());

        //Longer branching road
        c = new Coordinate[]{
                new Coordinate(0, 2, 1),
                new Coordinate(0, 1, 2),
                new Coordinate(0, 0, 2),
                new Coordinate(-1, 0, 2),
                new Coordinate(-2, 0, 1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(2, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withTwoPlayersSecondPlayerTakesOver_returnsSecondPlayer() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());

        //Longer branching road
        c = new Coordinate[]{
                new Coordinate(0, 3, 1),
                new Coordinate(0, 2, 1),
                new Coordinate(0, 1, 2),
                new Coordinate(0, 0, 2),
                new Coordinate(-1, 0, 2),
                new Coordinate(-2, 0, 1),
                new Coordinate(-2, 0, 0)
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(2, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(2, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_playerTwoInterruptedByPlayerOne_returnsFirstPlayer() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());

        //Longer branching road
        c = new Coordinate[]{
                new Coordinate(0, 3, 1),
                new Coordinate(0, 2, 1),
                new Coordinate(0, 1, 2),
                new Coordinate(0, 0, 2),
                new Coordinate(-1, 0, 2),
                new Coordinate(-2, 0, 1),
                new Coordinate(-2, 0, 0)
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(2, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(2, b.getLongestRoadOwnerID());

        b.createNewSettlement(new Coordinate(0, 0, 2), 1);
        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_playerTwoInterruptedByPlayerOne_playerTwoKeepsLongest() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(2, 0, -1),
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(2, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(2, b.getLongestRoadOwnerID());

        //Longer branching road
        c = new Coordinate[]{
                new Coordinate(0, 3, 1),
                new Coordinate(0, 2, 1),
                new Coordinate(0, 1, 2),
                new Coordinate(0, 0, 2),
                new Coordinate(-1, 0, 2),
                new Coordinate(-2, 0, 1),
                new Coordinate(-2, 0, 0),
                new Coordinate(-1, 0, 0),
                new Coordinate(0, -1, 0)
        };

        for (int i = 1; i < c.length; i++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());

        b.createNewSettlement(new Coordinate(-2, 0, 0), 2);
        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_fiveRoadsBuiltInconsistent() {
        Board b = new Board();

        Coordinate[] c = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(2, 1, 0),
                new Coordinate(2, 0, 0),
                new Coordinate(1, 0, 0)
        };

        b.createNewRoad(1, c[0], c[1]);
        b.createNewRoad(1, c[2], c[1]);
        b.createNewRoad(1, c[2], c[3]);
        b.createNewRoad(1, c[4], c[3]);
        b.createNewRoad(1, c[4], c[5]);

        b.updateLongestRoad();
        assertEquals(1, b.getLongestRoadOwnerID());
    }

}
