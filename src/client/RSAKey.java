package client;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class dealing with RSA encryption and decryption.
 */
public class RSAKey {
	protected static int BLOCKING_SIZE = 1;
	
	private long d;
	private long e;
	private long n;
	
	private long p;
	private long q;
	
	/**
	 * Generates a RSA Key with all negative values.
	 */
	private RSAKey() {
		this.d = -1;
		this.e = -1;
		this.n = -1;
		
		this.p = -1;
		this.q = -1;
	}
	
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
		for (int i = 2; i < key.n; ++i) {
			final long MAX = key.n - 1;
			final long MIN = 1;
			
			key.e = i;
			if (areRelativelyPrime(key.e, phi)) {
				break;
			}
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
	 * Decrypts the specified message, using the given public key and returns
	 * the message.
	 * @param msg The message blocks.
	 * @param d The public key d value.
	 * @param n The public key n value.
	 * @return The string representation of the message.
	 */
	public static String decrypt(ArrayList<Long> msg, long d, long n) {
		StringBuilder sB = new StringBuilder();
		
		for (long block : msg) {
			char c = (char)(RSAKey.modExp(block, d, n));
			sB.append(c);
		}
		
		return sB.toString();
	}
	
	/**
	 * Encrypts the specified message, using the given private key and returns
	 * the blocks of the encrypted message.
	 * @param msg The message to be encrypted.
	 * @param e The private key d value.
	 * @param n The private key n value.
	 * @return The list of long integers, where each integer represents a
	 *         block of the message.
	 */
	public static ArrayList<Long> encrypt(String msg, long e, long n) {
		ArrayList<Long> list = new ArrayList<Long>();
		
		// Go through each block of the message string 
		for (int i = 0; i < msg.length(); i += BLOCKING_SIZE) {			
			long eB = 0;
			
			// Get the sub string of the current block
			int endIndex = Math.min(i + BLOCKING_SIZE, msg.length());
			String msgSubStr = msg.substring(i, endIndex);
			
			// Go through each character in the block, generate the
			// encrypted character for it, and add to the encrypted
			// block.
			for (int j = 0; j < msgSubStr.length(); ++j) {
				char c = msgSubStr.charAt(j); 
				long eC = RSAKey.modExp(c, e, n);
				eB += eC;
				
				//System.out.println(eC);
			}
			
			// Add the block to the list
			list.add(eB);
		}
		
		return list;
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
	
	/**
	 * Computes (x ** y) mod m using modular exponentiation.
	 * @param x The base value.
	 * @param y The power value.
	 * @param m The mod value.
	 * @return The result of the modular exponentiation.
	 */
	protected static long modExp(long x, long y, long m) {
		long ret = 1;
		x = x % m;
		
		while (y > 0) {
			if (y % 2 != 0) {
				ret = (ret * x) % m; 
			}
			
			y >>= 1;
			x = (x * x) % m;
		}
		
		return ret;
	}
}
