
public class Recursive {
	private int limit;
	private long time;
	private int result;
		
		public int fibonacci(int n) {
			if (n == 0) {
				return 0;
			}
			if (n == 1) {
				return 1;
			}
			return fibonacci(n-1) + fibonacci(n-2);
		}
		
		public void run() {
			long start = System.nanoTime();
			result = fibonacci(limit);
			long finish = System.nanoTime();
			time = finish-start;
			System.out.println("Recursion Thread found the answer: " + result + " in " + time + " nanoseconds");
		}

		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}
		
}
