package client;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RSAKeyTest {
	/**
	 * Tests the generateRSAKey of the RSA class throws when the arguments
	 * are not valid.
	 */
	@Test
	public void testGenerateRSAKeyThrow() {
		// Assert throws if too small (p * q < 128 ** BLOCK_SIZE)
		try {
			RSAKey.generateRSAKey(13, 17);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// Assert throws if not prime
		try {
			RSAKey.generateRSAKey(1000000, 2000000);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	/**
	 * Tests the generateRSAKey of the RSA class for valid p and q values.
	 */
	@Test
	public void testGenerateRSAKey() {
		long p = 17033;
		long q = 17029;
		
		RSAKey rsa = RSAKey.generateRSAKey(p, q);
		
		// Assert N is valid
		assertEquals(290054957, rsa.getN());
		
		// Assert E is valid
		assertTrue(rsa.getE() < rsa.getN());
		assertTrue(RSAKey.areRelativelyPrime(rsa.getE(), rsa.getN()));
		
		// Assert D is valid
		long phi = (p - 1) * (q - 1);
		assertEquals(1, (rsa.getE() * rsa.getD()) % phi);
	}
	
	/**
	 * Tests the encrypt/decrypt methods of the RSA Key class.
	 */
	@Test
	public void testEncryptDecrypt() {
		long p = 17033;
		long q = 17029;
		RSAKey rsa = RSAKey.generateRSAKey(p, q);
		
		String original = "Meet me outside SCE at 10pm...";
		ArrayList<BigInteger> encrypted = RSAKey.encrypt(
				original, rsa.getE(), rsa.getN());
		String decrypted = RSAKey.decrypt(
				encrypted, rsa.getD(), rsa.getN());
		
		assertEquals(original, decrypted);
	}
	
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
				assertTrue(RSAKey.isPrime(i));
			} else {
				assertFalse(RSAKey.isPrime(i));
			}
		}
	}
	
	/**
	 * Tests the areRelativelyPrime function of the RSA class.
	 */
	@Test
	public void testAreRelativelyPrime() {
		assertTrue(RSAKey.areRelativelyPrime(2, 3));
		assertTrue(RSAKey.areRelativelyPrime(2, 11));
		assertFalse(RSAKey.areRelativelyPrime(32, 44));
		assertFalse(RSAKey.areRelativelyPrime(54, 24));
		assertFalse(RSAKey.areRelativelyPrime(12, 36));
		assertFalse(RSAKey.areRelativelyPrime(32, 64));
	}
	
	/**
	 * Tests the gcd function of the RSA class.
	 */
	@Test
	public void testGCD() {
		// Random test cases
		assertEquals(1, RSAKey.gcd(2, 3));
		assertEquals(1, RSAKey.gcd(2, 11));
		assertEquals(4, RSAKey.gcd(32, 44));
		assertEquals(6, RSAKey.gcd(54, 24));
		assertEquals(12, RSAKey.gcd(12, 36));
		assertEquals(32, RSAKey.gcd(32, 64));
	}
	
	/**
	 * Tests the modExp function of the RSA class.
	 */
	@Test
	public void testModExp() {
		// Random test cases
		assertEquals(368, RSAKey.modExp(512, 2015, 1000));
		assertEquals(868, RSAKey.modExp(20312, 2123015, 10100));
	}
}
