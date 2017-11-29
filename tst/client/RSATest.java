package client;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RSATest {
	/**
	 * Tests the isPrime function of the RSA class.
	 */
	@Test
	public void testIsPrime() {
		// Generate list of primes below 50
		long primes[] = {
				2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47
		};
		Set<Long> primesList = new HashSet<Long>();
		for (long p : primes) {
			primesList.add(p);
		}
		
		// Go through first 50 integers, check integers in the primes list are
		// considered primes, 
		for (long i = 1; i <= 50; ++i) {
			if (primesList.contains(i)) {
				assertTrue(RSA.isPrime(i));
			} else {
				assertFalse(RSA.isPrime(i));
			}
		}
	}
	
	/**
	 * Tests the areRelativelyPrime function of the RSA class.
	 */
	@Test
	public void testAreRelativelyPrime() {
		assertTrue(RSA.areRelativelyPrime(2, 3));
		assertTrue(RSA.areRelativelyPrime(2, 11));
		assertFalse(RSA.areRelativelyPrime(32, 44));
		assertFalse(RSA.areRelativelyPrime(54, 24));
		assertFalse(RSA.areRelativelyPrime(12, 36));
		assertFalse(RSA.areRelativelyPrime(32, 64));
	}
	
	/**
	 * Tests the gcd function of the RSA class.
	 */
	@Test
	public void testGCD() {
		// Random test cases
		assertEquals(1, RSA.gcd(2, 3));
		assertEquals(1, RSA.gcd(2, 11));
		assertEquals(4, RSA.gcd(32, 44));
		assertEquals(6, RSA.gcd(54, 24));
		assertEquals(12, RSA.gcd(12, 36));
		assertEquals(32, RSA.gcd(32, 64));
	}
}
