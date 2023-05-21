package catan.game;

import catan.logic.Game;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
//CHECKSTYLE:OFF: checkstyle:magicnumber
public class GameTurnIntegrationTests {
    @Test
    public void rollDie_CanMatchExpectedRange() {
        Random r = new Random();
        Game game = new Game(r);

        int result = game.rollDie();
        assertTrue(result >= 2 && result <= 12);
    }


}
