package catan;

import org.junit.jupiter.api.Test;

import catan.logic.Coordinate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public final class CoordinateTest {
	@Test
	public void testEquals_withAllZero_expectTrue() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 0, 0);

		assertEquals(a, b);
	}

	@Test
	public void testEquals_withDifferentBoundaryValues_expectTrue() {
		Coordinate a = new Coordinate(Integer.MAX_VALUE, 0, Integer.MIN_VALUE);
		Coordinate b = new Coordinate(Integer.MAX_VALUE, 0, Integer.MIN_VALUE);

		assertEquals(a, b);
	}

	@Test
	public void testEquals_onlyDifferentX_expectFalse() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(1, 0, 0);

		assertNotEquals(a, b);
	}

	@Test
	public void testEquals_onlyDifferentY_expectFalse() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 1, 0);

		assertNotEquals(a, b);
	}

	@Test
	public void testEquals_onlyDifferentZ_expectFalse() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 0, 1);

		assertNotEquals(a, b);
	}

	//CHECKSTYLE:OFF: checkstyle:magicnumber
	@Test
	public void testEquals_sameCoordinate_expectTrue() {
		Coordinate a = new Coordinate(1, 3, 5);
		assertEquals(a, a);
	}
	//CHECKSTYLE:ON: checkstyle:magicnumber

	//CHECKSTYLE:OFF: checkstyle:magicnumber
	@Test
	public void testEquals_notACoordinate_expectFalse() {
		ArrayList<Integer> dummy = new ArrayList<Integer>();
		Coordinate a = new Coordinate(1, 2, 3);
		assertNotEquals(a, dummy);
	}
	//CHECKSTYLE:ON: checkstyle:magicnumber

	//CHECKSTYLE:OFF: checkstyle:magicnumber
	@Test
	public void testHashing_basicCoordinate_expectEqual() {
		int expected = "1,2,3".hashCode();
		Coordinate a = new Coordinate(1, 2, 3);
		assertEquals(a.hashCode(), expected);
	}
	//CHECKSTYLE:ON: checkstyle:magicnumber

}
