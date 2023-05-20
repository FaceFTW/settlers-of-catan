package catan;

import catan.data.ResourceType;
import catan.logic.Board;
import catan.logic.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
}
