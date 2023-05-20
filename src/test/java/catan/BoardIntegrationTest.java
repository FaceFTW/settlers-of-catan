package catan;

import catan.data.Player;
import catan.data.ResourceType;
import catan.data.Tile;
import catan.logic.Board;
import catan.logic.Coordinate;
import catan.logic.Utils;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardIntegrationTest {
    @Test
    public void testConstructor() {
        Random r = new Random();
        Board board = new Board(r);

        ArrayList<ResourceType> allNecessaryResources = new ArrayList<>(Arrays.asList(Utils.ALL_TILES_RESOURCES));

        for (int i = 0; i < Utils.TILES_SPIRAL_LOCATION.length; i++) {
            ResourceType resource = board.getTiles()[i].getResourceType();
            assertTrue(allNecessaryResources.remove(resource));
            assertEquals(Utils.TILES_SPIRAL_LOCATION[i], board.getTiles()[i].getPosition());
        }

        assertTrue(allNecessaryResources.isEmpty());

        int desertEffect = 0;
        for (int i = 0; i < Utils.TILES_SPIRAL_LOCATION.length - 1; i++) {
            if (board.getTiles()[i].getResourceType() == ResourceType.DESERT) {
                desertEffect = -1;
                continue;
            }
            assertEquals(Utils.TILES_ROLL_NUMBERS[i + desertEffect],
                    board.getTiles()[i].getDieRoll());
        }
    }

    // CHECKSTYLE:OFF: checkstyle:magicnumber
    @Test
    public void testCreateBoard_lookAtSpiralStart_checkCorrectCornerPlacement() {
        ArrayList<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(-2, 0, 1));
        expected.add(new Coordinate(-1, 0, 2));
        expected.add(new Coordinate(-1, 0, 3));
        expected.add(new Coordinate(-2, 0, 3));
        expected.add(new Coordinate(-3, 0, 2));
        expected.add(new Coordinate(-3, 0, 1));

        Board board = new Board(new Random());
        Tile tileInQuestion = board.getTiles()[0];

        for (Coordinate c : tileInQuestion.getCorners()) {
            assertTrue(expected.remove(c));
        }

        assertTrue(expected.isEmpty());
    }
    // CHECKSTYLE:ON: checkstyle:magicnumber

    @Test
    public void testCreateBoard_lookAtSpiralEnd_checkCorrectCornerPlacement() {
        ArrayList<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(-1, 0, 0));
        expected.add(new Coordinate(1, 0, 0));
        expected.add(new Coordinate(0, -1, 0));
        expected.add(new Coordinate(0, 1, 0));
        expected.add(new Coordinate(0, 0, -1));
        expected.add(new Coordinate(0, 0, 1));

        Board board = new Board(new Random());
        Tile tileInQuestion = board.getTiles()[Utils.TILES_SPIRAL_LOCATION.length - 1];

        for (Coordinate c : tileInQuestion.getCorners()) {
            assertTrue(expected.remove(c));
        }

        assertTrue(expected.isEmpty());
    }


}
