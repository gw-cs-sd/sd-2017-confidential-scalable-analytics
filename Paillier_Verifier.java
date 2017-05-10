/*
 * Libraries and packages used:
 */

package verifierPackage;
import verifierPackage.SwingApp;

import java.math.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.io.*;



/*
 * Decrypt given ciphertextResult with given keys
 *
 * Returns result
 */
public class Paillier_Verifier {

    /**
     * p and q are two large primes. 
     * lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1, q-1).
     */
    private static BigInteger lambda;
    /**
     * n = p*q, where p and q are two large primes.
     */
    public static BigInteger n;
    /**
     * nsquare = n*n
     */
    public static BigInteger nsquare;
    /**
     * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
     */
    private static BigInteger g;

    public Paillier_Verifier(String[] str) {

    	lambda  = new BigInteger(str[0]);
    	g 	    = new BigInteger(str[1]);
    	n 	    = new BigInteger(str[2]);
    	nsquare = n.multiply(n);
    }

	/**
     * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where u = (L(g^lambda mod n^2))^(-1) mod n.
     * @param c ciphertext as a BigInteger
     * @return plaintext as a BigInteger
     */
    public static BigInteger Decryption(BigInteger c) {
        BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
    }

    public String returnValue0(BigInteger decryptedResult) {

    	String result = String.valueOf(decryptedResult);

    	return result;
    }


    // str = [lambda, g, n, cipherTextResultMask]
    public static void main(String[] str) throws IOException {

    	Paillier_Verifier paillier = new Paillier_Verifier(str);
    	
    	BigInteger cipherTextResultMask = new BigInteger(str[3]);

    	BigInteger decryptedResult;
    	 
    	String finalResult;
    	
    	/*
		 * Establish connection to eVERIFY server, encode results and send data.
		 */
		 
		URL updateTransaction = new URL("http://localhost/everify/updateTransaction.php");
	    URLConnection eVerifyConnection = updateTransaction.openConnection();
	    
	    String charset = "UTF-8";
	    eVerifyConnection.setDoOutput(true);

    	/*
    	 * Result does not exceed minimum qualifying balance
    	 * FAILURE
    	 */
        if (paillier.Decryption(cipherTextResultMask).compareTo(new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")) == 1){
            decryptedResult = paillier.Decryption(cipherTextResultMask).subtract(n);
            finalResult = "FAIL";
        }
     	/*
    	 * Result does exceed minimum qualifying balance
    	 * SUCCESS
    	 */
        else {
            decryptedResult = paillier.Decryption(cipherTextResultMask);
        	finalResult = "PASS";
        }

    	String value0 = paillier.returnValue0(decryptedResult);
	   
	    String parameters = "result=" + finalResult + "&transactionId=" + SwingApp.verificationId;  
	    
	    byte[] parameterBytes = parameters.getBytes(charset);
	    
	    DataOutputStream sendParameters = new DataOutputStream(eVerifyConnection.getOutputStream());
	    sendParameters.write(parameterBytes);
	    sendParameters.close();
        
        eVerifyConnection.getInputStream();
    	
    	SwingApp.sendResult(value0, finalResult);

    }
}