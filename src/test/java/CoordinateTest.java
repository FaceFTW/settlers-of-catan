import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
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
}
