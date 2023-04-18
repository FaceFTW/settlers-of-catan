import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
	@Test
	public void testEquals_withAllZero_expectTrue() {
		Coordinate a = new Coordinate(0, 0, 0);
		Coordinate b = new Coordinate(0, 0, 0);

		assertTrue(a.equals(b));
	}
}
