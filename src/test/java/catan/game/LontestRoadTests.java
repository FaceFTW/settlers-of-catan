package catan.game;

import catan.logic.Board;
import catan.logic.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LontestRoadTests {
    @Test
    public void testUpdateLongestRoad_withEmptyBoard_returnsNegativeOne () {
        Board b = new Board();
        b.updateLongestRoad();
        assertEquals(-1, b.getLongestRoadOwnerID());
    }

    @Test
    public void testUpdateLongestRoad_withRoadFourLong_returnsNegativeOne () {
        Board b = new Board();

        Coordinate[] c = new Coordinate[] {
                new Coordinate(0, 1, 0),
                new Coordinate(0, 2, 0),
                new Coordinate(1, 2, 0),
                new Coordinate(1, 1, 1)
        };

        for (int i = 1;i < c.length;i ++) {
            b.createNewRoad(1, c[i - 1], c[i]);
        }

        b.updateLongestRoad();
        assertEquals(-1, b.getLongestRoadOwnerID());
    }

}
