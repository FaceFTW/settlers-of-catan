package catan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import catan.logic.Coordinate;
import catan.logic.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("checkstyle:magicnumber")
public final class UtilsTest {
	@ParameterizedTest
	@ValueSource(strings = {
			"-4,-4,-4|-5,-4,-4,-3,-4,-4,-4,-5,-4,-4,-3,-4,-4,-4,-5,-4,-4,-3",
			"-4,-4,0|-5,-4,0,-3,-4,0,-4,-5,0,-4,-3,0,-4,-4,-1,-4,-4,1",
			"-4,-4,4|-5,-4,4,-3,-4,4,-4,-5,4,-4,-3,4,-4,-4,3,-4,-4,5",
			"-4,0,-4|-5,0,-4,-3,0,-4,-4,-1,-4,-4,1,-4,-4,0,-5,-4,0,-3",
			"-4,0,0|-5,0,0,-3,0,0,-4,-1,0,-4,1,0,-4,0,-1,-4,0,1",
			"-4,0,4|-5,0,4,-3,0,4,-4,-1,4,-4,1,4,-4,0,3,-4,0,5",
			"-4,4,-4|-5,4,-4,-3,4,-4,-4,3,-4,-4,5,-4,-4,4,-5,-4,4,-3",
			"-4,4,0|-5,4,0,-3,4,0,-4,3,0,-4,5,0,-4,4,-1,-4,4,1",
			"-4,4,4|-5,4,4,-3,4,4,-4,3,4,-4,5,4,-4,4,3,-4,4,5",
			"0,-4,-4|-1,-4,-4,1,-4,-4,0,-5,-4,0,-3,-4,0,-4,-5,0,-4,-3",
			"0,-4,0|-1,-4,0,1,-4,0,0,-5,0,0,-3,0,0,-4,-1,0,-4,1",
			"0,-4,4|-1,-4,4,1,-4,4,0,-5,4,0,-3,4,0,-4,3,0,-4,5",
			"0,0,-4|-1,0,-4,1,0,-4,0,-1,-4,0,1,-4,0,0,-5,0,0,-3",
			"0,0,0|-1,0,0,1,0,0,0,-1,0,0,1,0,0,0,-1,0,0,1",
			"0,0,4|-1,0,4,1,0,4,0,-1,4,0,1,4,0,0,3,0,0,5",
			"0,4,-4|-1,4,-4,1,4,-4,0,3,-4,0,5,-4,0,4,-5,0,4,-3",
			"0,4,0|-1,4,0,1,4,0,0,3,0,0,5,0,0,4,-1,0,4,1",
			"0,4,4|-1,4,4,1,4,4,0,3,4,0,5,4,0,4,3,0,4,5",
			"4,-4,-4|3,-4,-4,5,-4,-4,4,-5,-4,4,-3,-4,4,-4,-5,4,-4,-3",
			"4,-4,0|3,-4,0,5,-4,0,4,-5,0,4,-3,0,4,-4,-1,4,-4,1",
			"4,-4,4|3,-4,4,5,-4,4,4,-5,4,4,-3,4,4,-4,3,4,-4,5",
			"4,0,-4|3,0,-4,5,0,-4,4,-1,-4,4,1,-4,4,0,-5,4,0,-3",
			"4,0,0|3,0,0,5,0,0,4,-1,0,4,1,0,4,0,-1,4,0,1",
			"4,0,4|3,0,4,5,0,4,4,-1,4,4,1,4,4,0,3,4,0,5",
			"4,4,-4|3,4,-4,5,4,-4,4,3,-4,4,5,-4,4,4,-5,4,4,-3",
			"4,4,0|3,4,0,5,4,0,4,3,0,4,5,0,4,4,-1,4,4,1",
			"4,4,4|3,4,4,5,4,4,4,3,4,4,5,4,4,4,3,4,4,5"
	})

	// CHECKSTYLE:OFF: LineLength
	public void testGetAdjacent_allInputs_returnsListOfSixTuples(String values) {
		String[] set = values.split("\\|");

		String[] inputValuesAsStrings = set[0].split(",");
		String[] expectedOutputValuesAsStrings = set[1].split(",");

		int[] inputValues = new int[3];
		int[] expectedOutputValues = new int[18];

		for (int i = 0; i < 3; i++) {
			inputValues[i] = Integer.parseInt(inputValuesAsStrings[i]);
		}

		for (int i = 0; i < 18; i++) {
			expectedOutputValues[i] = Integer.parseInt(expectedOutputValuesAsStrings[i]);
		}

		Coordinate input = new Coordinate(inputValues[0], inputValues[1], inputValues[2]);

		ArrayList<Coordinate> expectedOutputs = new ArrayList<Coordinate>();

		for (int i = 0; i < 18; i += 3) {
			expectedOutputs.add(new Coordinate(expectedOutputValues[i],
					expectedOutputValues[i + 1],
					expectedOutputValues[i + 2]));
		}

		Coordinate[] actualOutput = Utils.getAdjacent(input);
		for (int i = 0; i < 6; i++) {
			assertTrue(expectedOutputs.contains(actualOutput[i]));
			expectedOutputs.remove(actualOutput[i]);
		}
	}
	// CHECKSTYLE:ON: LineLength

	@ParameterizedTest
	@ValueSource(strings = {
			// zero case (needs no change)
			"0,0,0,0,0,0",
			// single value (needs no change)
			"5,0,0,5,0,0",
			"-5,0,0,-5,0,0",
			"0,5,0,0,5,0",
			"0,-5,0,0,-5,0",
			"0,0,5,0,0,5",
			"0,0,-5,0,0,-5",
			// dual value (needs no change)
			"-5,-5,0,-5,-5,0",
			"-5,0,5,-5,0,5",
			"0,-5,-5,0,-5,-5",
			"0,-5,-5,0,-5,-5",
			"5,0,-5,5,0,-5",
			"5,5,0,5,5,0",
			// dual value (needs to change)
			"-5,0,-5,0,-5,0",
			"-5,5,0,0,0,5",
			"0,-5,5,-5,0,0",
			"0,5,-5,5,0,0",
			"5,-5,0,0,0,-5",
			"5,0,5,0,5,0",
			// tri value (needs to change)
			"-5,-5,-5,0,-10,0",
			"-5,-5,5,-10,0,0",
			"-5,5,-5,0,0,0",
			"-5,5,5,0,0,10",
			"5,-5,-5,0,0,-10",
			"5,-5,5,0,0,0",
			"5,5,-5,10,0,0",
			"5,5,5,0,10,0",
			"3,1,1,2,2,0"
	})
	public void testResolveToValid_allBoundries_returnsValidTuple(String values) {
		String[] valuesAsStrings = values.split(",");
		int[] valuesAsInts = new int[6];

		for (int i = 0; i < 6; i++) {
			valuesAsInts[i] = Integer.parseInt(valuesAsStrings[i]);
		}

		Coordinate input = new Coordinate(valuesAsInts[0], valuesAsInts[1], valuesAsInts[2]);
		Coordinate expected = new Coordinate(valuesAsInts[3], valuesAsInts[4], valuesAsInts[5]);

		Coordinate actual = Utils.resolveToValid(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testIsRealCoordinate_withRealCoordinate_returnsTrue() {
		Coordinate input = new Coordinate(0, 0, 0);
		assertTrue(Utils.isRealCoordinate(input));
	}

	@Test
	public void testIsRealCoordinate_withUnRealCoordinate_returnsFalse() {
		Coordinate input = new Coordinate(5, 0, 0);
		assertFalse(Utils.isRealCoordinate(input));
	}

	@Test
	void testGetBoardAdjacents_GivenTileCenter() {
		Coordinate input = new Coordinate(0, 0, 0);
		Coordinate[] expectedArray = {
				new Coordinate(0, 0, 1),
				new Coordinate(0, 1, 0),
				new Coordinate(1, 0, 0),
				new Coordinate(0, 0, -1),
				new Coordinate(0, -1, 0),
				new Coordinate(-1, 0, 0)
		};

		Coordinate[] actualOutput = Utils.getBoardAdjacents(input);

		assertEquals(6, actualOutput.length);
		for (int i = 0; i < 6; i++) {
			assertTrue(Arrays.asList(expectedArray).contains(actualOutput[i]));
		}
	}

	@Test
	void testGetBoardAdjacents_GivenTileCorner() {
		Coordinate input = new Coordinate(1, 0, 0);
		Coordinate[] expectedArray = {
				new Coordinate(2, 0, 0),
				new Coordinate(0, 1, 0),
				new Coordinate(0, 0, -1),
		};

		Coordinate[] actualOutput = Utils.getBoardAdjacents(input);

		assertEquals(3, actualOutput.length);
		for (int i = 0; i < 3; i++) {
			assertTrue(Arrays.asList(expectedArray).contains(actualOutput[i]));
		}
	}
}
