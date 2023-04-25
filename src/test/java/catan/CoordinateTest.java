package catan;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public final class CoordinateTest {
	@Test
	public void testEquals_withAllZero_expectTrue() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 0, 0);

		assertTrue(a.equals(b));
	}

	@Test
	public void testEquals_withDifferentBoundaryValues_expectTrue() {
		Coordinate a = new Coordinate(Integer.MAX_VALUE, 0, Integer.MIN_VALUE);
		Coordinate b = new Coordinate(Integer.MAX_VALUE, 0, Integer.MIN_VALUE);

		assertTrue(a.equals(b));
	}

	@Test
	public void testEquals_onlyDifferentX_expectFalse() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(1, 0, 0);

		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals_onlyDifferentY_expectFalse() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 1, 0);

		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals_onlyDifferentZ_expectFalse() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 0, 1);

		assertFalse(a.equals(b));
	}

	@Test
	public void testEquals_sameCoordinate_expectTrue() {
		Coordinate a = new Coordinate(1, 3, 5);
		assertTrue(a.equals(a));
	}

	@Test
	public void testEquals_notACoordinate_expectFalse() {
		ArrayList<Integer> dummy = new ArrayList<Integer>();
		Coordinate a = new Coordinate(1, 2, 3);
		assertFalse(a.equals(dummy));
	}

	@Test
	public void testHashing_basicCoordinate_expectEqual() {
		int expected = "1,2,3".hashCode();
		Coordinate a = new Coordinate(1, 2, 3);
		assertEquals(a.hashCode(), expected);
	}


}
