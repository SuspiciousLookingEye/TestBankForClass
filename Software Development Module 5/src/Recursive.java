
/**
 * This class utilizes recursive code execution and memory access to calculate the Fibonacci sequence to a set limit.
 * It also provides a calculated output time in nanoseconds.
 * @author Jordan Roig
 * @since 15.02+7
 *
 */
public class Recursive {
	private int limit;
	private long time;
	private long result;
		
		/**
		 * This method calculates the Fibonacci sequence by taking the limit variable and continuously calculating the sum of values of the two terms using the if statements and previously stored values as guidelines.
		 * This repeats for n terms, and when the loop ends, it returns the final sum found.
		 * <p>Note that, due to the data limit of long signed values, that negative numbers become increasingly likely to occur at high term counts due to data overflow.</p>
		 * @param n This integer variable represents the last term of the sequence that will be calculated, and its value is assigned by passing limit to it.
		 * @return Returns the either the values 0 or 1 when provided n = 0 or n = 1 (as these are the starting seed values) or otherwise the sum of the previous two sums in the sequence.
		 */
		public long fibonacci(int n) {
			if (n == 0) {
				return 0;
			}
			if (n == 1) {
				return 1;
			}
			return fibonacci(n-1) + fibonacci(n-2);
		}
		
		/**
		 * This method first logs the starting nanosecond of the JVM timesource, then runs the fibonacci() method and calculates the nanosecond of the timesource at the time of completion.
		 * It calculates the difference between the two to determine the time taken, in nanoseconds, to complete the program, and provides a status message indicating the result found and the time taken to do so.
		 */
		public void run() {
			long start = System.nanoTime();
			result = fibonacci(limit);
			long finish = System.nanoTime();
			time = finish-start;
			System.out.println("Recursion Thread found the answer: " + result + " in " + time + " nanoseconds");
		}

		/**This method retrieves the current limit value set for the recursive loop.
		 * This is intended for debugging scenarios where the loop has ended up at an unknown position.
		 * @return Returns the current integer value set for the loop as its term limit.
		 */
		public int getLimit() {
			return limit;
		}

		/**
		 * This method sets the current limit for the loop to the integer parameter specified. 
		 * This method is required to set the limit of Dynamic objects before the fibonacci() method is ran.
		 * @param limit The last term that the loop will calculate up to as an integer.
		 */
		public void setLimit(int limit) {
			this.limit = limit;
		}

		/**This method retrieves the nanosecond difference before and after loop execution. 
		 * The method is required to retrieve time values for use on ResultsGraph objects, specifically their XYSeries data.
		 * @return Returns the long difference in nanoseconds between the JVM timesource before and after execution of the fibonacci() method.
		 */
		public long getTime() {
			return time;
		}

		/**
		 * This method sets the time value to be the long parameter provided.
		 * This method is intended for debugging the output of the ResultsGraph plot by allowing for the creation of manual data points.
		 * @param time The long time difference in nanoseconds between the beginning of loop execution and afterward as recorded by JVM.
		 */
		public void setTime(long time) {
			this.time = time;
		}
		
}
