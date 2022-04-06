 
public class ParallelThreadSum extends Thread {

    private double [] arr;

    private int low, high;

	double partial;
	//upon use of the constructor, calls a source array and calculates range values. This is used to determine thread ranges.
    public ParallelThreadSum(double [] sourceArray, int low, int high)
    {
        this.arr = sourceArray;
        this.low = low;
        this.high = Math.min(high, arr.length);
    }
    //retrieves a partial sum determined by the thread.
    public double getPartialSum()
    {
        return partial;
    }
    //required references for Runnable threads. Tells the threads how to complete summation using static method calls.
   
    public void run()
    {
        partial = sum(arr, low, high);
    }
    //Calculates time difference in execution and handles the sum() method to get an answer.
    public static void singleThreadSum(double[] arr)
    {
    	System.out.println("Single Thread Sum Started");
    	long startTime = System.currentTimeMillis();
    	double finalSum = sum(arr, 0, arr.length);
    	long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Single Thread calculated sum of " + finalSum + " in " + elapsedTime + " ms.");
    }
    //initializes a sum and adds values to it as they are found.
    public static double sum(double[] arr2, int low, int high)
    {
        int total = 0;

        for (int i = low; i < high; i++) {
            total += arr2[i];
        }

        return total;
    }
    //This method controls the overloaded parallelSum() method and provides timing statistics. It also provides the number of threads to split by.
    public static void parallelSum(double[] arr)
    {
        System.out.println("Parallel Thread Sum Started");
    	long startTime = System.currentTimeMillis();
    	double finalSum = parallelSum(arr, Runtime.getRuntime().availableProcessors());
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Parallel Thread calculated sum of " + finalSum + " in " + elapsedTime + " ms using " + Runtime.getRuntime().availableProcessors() + " threads.");
    }
    //This method splits the array by the number of threads passed to it, finds the partial array size, and has each array fill a partial sum value to be combined once all threads die. This value is returned to its controller method.
    public static double parallelSum(double[] arr, int threads)
    {
        //determines partial array size for threads
    	int size = (int) Math.ceil(arr.length * 1.0 / threads);
    	//sets container for final sum
        ParallelThreadSum[] sums = new ParallelThreadSum[threads];
        //instantiates ranges for threads and executes sum statement found in run()
        for (int i = 0; i < threads; i++) {
            sums[i] = new ParallelThreadSum(arr, i * size, (i + 1) * size);
            sums[i].start();
        }
        //waits for all threads to die off after finding sums.
        try {
            for (ParallelThreadSum sum : sums) {
                sum.join();
            }
        } catch (InterruptedException e) { }
        //instantiate final sum, and combines thread sums in for loop.
        double total = 0;

        for (ParallelThreadSum sum : sums) {
            total += sum.getPartialSum();
        }

        return total;
    }

}
