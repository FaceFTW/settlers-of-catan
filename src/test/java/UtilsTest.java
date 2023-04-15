import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.management.MBeanTrustPermission;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
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
	public void testGetAdjacent_allInputs_returnsListOfSixTuples(String values) {
		String[] set = values.split("\\|");

		String[] inputValuesAsStrings = set[0].split(",");
		String[] expectedOutputValuesAsStrings = set[1].split(",");

		int[] inputValues = new int[3];
		int[] expectedOutputValues = new int[18];

		for (int i = 0;i < 3;i ++)
			inputValues[i] = Integer.parseInt(inputValuesAsStrings[i]);

		for (int i = 0;i < 18;i ++)
			expectedOutputValues[i] = Integer.parseInt(expectedOutputValuesAsStrings[i]);

		Triplet<Integer, Integer, Integer> input = new Triplet<Integer, Integer, Integer>(inputValues[0], inputValues[1], inputValues[2]);

		ArrayList<Triplet<Integer, Integer, Integer>> expectedOutputs = new ArrayList<Triplet<Integer, Integer, Integer>>();
		for (int i = 0;i < 18;i += 3)
			expectedOutputs.add(new Triplet<Integer, Integer, Integer>(expectedOutputValues[i], expectedOutputValues[i + 1], expectedOutputValues[i + 2]));

		Triplet<Integer, Integer, Integer>[] actualOutput = Utils.getAdjacent(input);
		for (int i = 0;i < 6; i ++)
			assertTrue(expectedOutputs.contains(actualOutput[i]));
	}

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
		"5,5,0,5,5,0"
	})
	public void testResolveToValid_allBoundries_returnsValidTuple(String values) {
		String[] valuesAsStrings = values.split(",");
		int[] valuesAsInts = new int[6];

		for (int i = 0;i < 6;i ++)
			valuesAsInts[i] = Integer.parseInt(valuesAsStrings[i]);

		Triplet<Integer, Integer, Integer> input = new Triplet<Integer, Integer, Integer>(valuesAsInts[0], valuesAsInts[1], valuesAsInts[2]);
		Triplet<Integer, Integer, Integer> expected = new Triplet<Integer, Integer, Integer>(valuesAsInts[3], valuesAsInts[4], valuesAsInts[5]);

		Triplet<Integer, Integer, Integer> actual = Utils.resolveToValid(input);

		assertEquals(expected, actual);
	}
}