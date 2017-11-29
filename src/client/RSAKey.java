package client;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class dealing with RSA encryption and decryption.
 */
public class RSAKey {
	protected static int BLOCKING_SIZE = 4;
	
	private long d;
	private long e;
	private long n;
	
	private long p;
	private long q;
	
	/**
	 * Initializes an RSA instance with the specified p and q values.
	 * @param p The p prime.
	 * @param q The q prime.
	 */
	public static RSAKey generateRSAKey(long p, long q)
			throws IllegalArgumentException {
		RSAKey key = new RSAKey(); 
		
		// Set p and q of the instance
		key.p = p;
		key.q = q;
		
		// Check p and q are both prime, throw exception if not prime
		if (!isPrime(key.p)) {
			throw new IllegalArgumentException("p value is not prime");
		}
		if (!isPrime(key.q)) {
			throw new IllegalArgumentException("q value is not prime");
		}
		
		// Check p * q > 128 ** BLOCKING_SIZE
		key.n = key.p * key.q;
		if (key.n <= Math.pow(128, BLOCKING_SIZE)) {
			throw new IllegalArgumentException("p * q <= 128 ** B_S");
		}
		
		// Calculate phi
		long phi = (key.p - 1) * (key.q - 1);
		
		// Find some value of e (less than n and relatively prime to phi)
		key.e = key.n;
		while (key.e >= key.n || !areRelativelyPrime(key.e, phi)) {
			final long MAX = key.n - 1;
			final long MIN = 1;
			
			key.e = MIN + ThreadLocalRandom.current().nextLong(MAX - MIN + 1);
		}
		
		// Find the value of d such that (e * d) mod phi = 1
		for (int k = 1; ; ++k) {
			long numerator = 1 + k * phi;
			long denominator = key.e;
			
			if (numerator % denominator == 0) {
				key.d = numerator / denominator;
				break;
			}
		}
		
		return key;
	}
	
	/**
	 * Returns the d value.
	 * @return The d value.
	 */
	public long getD() {
		return this.d;
	}
	
	/**
	 * Returns the e value.
	 * @return The e value.
	 */
	public long getE() {
		return this.e;
	}
	
	/**
	 * Returns the n value.
	 * @return The n value.
	 */
	public long getN() {
		return this.n;
	}
	
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
