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


}
