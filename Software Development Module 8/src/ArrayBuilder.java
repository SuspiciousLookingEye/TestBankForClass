import java.util.Random;

public class ArrayBuilder {
	private int arraySize = 200000000;
	private double [] arrayObject;
	//calls method that chains together other methods in creating an array
	public ArrayBuilder() {
		gen_array(arraySize);
	}
	//lowest level method. This generates a random number between set values.
	public static double gen_rand(int min, int max) {
		Random rand = new Random();
		double random_double = min + rand.nextDouble() * (max -
				min);
		return random_double;
	}
	//This generates a set number of random values into a provided array
	public static void gen_numbers(double[] numbers, int how_many) {
		for (int i = 0; i < how_many; i++) {
			numbers[i] = gen_rand(0, 10);
		}
	}
	//This method executes upon construction. It creates an array of 200,000,000 double values, and fills it with generated numbers with the gen_numbers() call
	public double [] gen_array(int arraySize){
		System.out.println("Array creation started");
		double[] numbers = new double[arraySize];
		gen_numbers(numbers, arraySize);
		arrayObject = numbers;
		System.out.println("Array complete.");
		return numbers;
	}
	//This provides a getter to allow the main method to store results.
	public double[] getArrayObject() {
		return arrayObject;
	}
	
	
	
	
}
