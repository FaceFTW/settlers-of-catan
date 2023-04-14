import org.javatuples.Triplet;

public class Utils {
	public static Triplet<Integer, Integer, Integer>[] getAdjacent(Triplet<Integer, Integer, Integer> input) {
		int x = input.getValue0();
		int y = input.getValue1();
		int z = input.getValue2();

		Triplet<Integer, Integer, Integer>[] returnArray = new Triplet[6];
		returnArray[0] = new Triplet<Integer, Integer, Integer>(x - 1, y, z);
		returnArray[1] = new Triplet<Integer, Integer, Integer>(x + 1, y, z);
		returnArray[2] = new Triplet<Integer, Integer, Integer>(x, y - 1, z);
		returnArray[3] = new Triplet<Integer, Integer, Integer>(x, y + 1, z);
		returnArray[4] = new Triplet<Integer, Integer, Integer>(x, y, z - 1);
		returnArray[5] = new Triplet<Integer, Integer, Integer>(x, y, z + 1);

		return returnArray;
	}
}
