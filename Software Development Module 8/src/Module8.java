
public class Module8 {

	public static void main(String[] args) {
		//finds sums via single and parallel sums. Note that array construction can take several seconds to complete.
		ArrayBuilder arrayCreator = new ArrayBuilder();
		double [] sourceArray = arrayCreator.getArrayObject();
		ParallelThreadSum.singleThreadSum(sourceArray);
		ParallelThreadSum.parallelSum(sourceArray);
	}

}
