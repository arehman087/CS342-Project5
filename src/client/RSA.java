package client;

/**
 * Class dealing with RSA encryption and decryption.
 */
public class RSA {
	private final int BLOCKING_SIZE = 4;
	
	private long n;
	private long p;
	private long q;
	
	/**
	 * Checks if the two specified numbers are relatively prime.
	 * @param a The first number.
	 * @param b The second number.
	 * @return True if the numbers are relatively prime, false otherwise.
	 */
	protected static boolean areRelativelyPrime(long a, long b) {
		return gcd(a, b) == 1;
	}
	
	/**
	 * Checks if the specified number is prime.
	 * 
	 * Algorithm From: http://www.geeksforgeeks.org/
	 *     primality-test-set-1-introduction-and-school-method/
	 * @param n The number.
	 * @return True if the number is prime, false otherwise.
	 */
	protected static boolean isPrime(long n) {
		if (n <= 1) {
			return false;
		} else {
			for (long i = 2; i < n; ++i) {
				if (n % i == 0) {
					return false;
				}
			}
			
			return true;
		}
	}
	
	/**
	 * Returns the greatest common divisor for the specified numbers.
	 * 
	 * Algorithm From: https://en.wikipedia.org/wiki/Euclidean_algorithm
	 * @param a The first number.
	 * @param b The second number.
	 * @return The greatest common divisor.
	 */
	protected static long gcd(long a, long b) {
		while (b != 0) {
			long t = b;
			
			b = a % b;
			a = t;
		}
		
		return a;
	}
}
