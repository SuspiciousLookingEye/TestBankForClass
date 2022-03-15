
public class Dynamic {
	private int limit;
	private long time;
	private int result;
	
	public int fibonacci(int n) {
		int var1 = 0;
		int var2 = 1;
		int var3 = 0;
		
		for (int i = 2; i <= n; i++) {
			var3 = var1 + var2;
			var1 = var2;
			var2 = var3;
		}
		return var3;
	}
	
	public void run() {
		long start = System.nanoTime();
		result = fibonacci(limit);
		long finish = System.nanoTime();
		time = finish-start;
		System.out.println("Dynamic Thread found the answer: " + result + " in " + time + " nanoseconds");
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
