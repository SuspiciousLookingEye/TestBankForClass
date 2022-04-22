
public class PrimeCalculator {
	public boolean isPrime(long testNumber) {
		if (testNumber <= 1) {
			return false;
		}
		
		for (int x = 2; x < Math.sqrt(testNumber); x++) {
			if (testNumber % x == 0) {
				return false;
			}
		}
		return true;
	}
}
