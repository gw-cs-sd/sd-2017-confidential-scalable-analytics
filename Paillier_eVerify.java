import java.math.*;
import java.util.*;
import java.io.*;

/*
 * Sum encryped accounts balances
 * Subtract from cipherTextSum cipherThreshold
 * Apply mask
 *
 * Returns cipherTextSumMask
 */
public class Paillier_eVerify {

	private static BigInteger n, nsquare;


	public Paillier_eVerify(String[] str) {

		n = new BigInteger(str[1]);
		nsquare = n.multiply(n);
	}


	/*
	 * Sums an EVEN number of ciphertexts (Checking + Savings)
	 */
    public BigInteger ciphertextSummation(String[] ciphertextList) {

        BigInteger ciphertext1;
        BigInteger ciphertext2;
        BigInteger ciphertextSum = new BigInteger("1");

        for (int n = 0; n < ciphertextList.length; n = n + 2) 
        {

            ciphertext1 = new BigInteger(ciphertextList[n]);
            ciphertext2 = new BigInteger(ciphertextList[n + 1]);

            ciphertextSum = ciphertextSum.multiply(ciphertext1).multiply(ciphertext2);

        }

        ciphertextSum = ciphertextSum.mod(nsquare);

        return ciphertextSum;
    } 

    /*
     * RETURNS:
     * "cipherTextResult"
     */
    public void returnValue(BigInteger cipherTextResultMask) {
    	System.out.println(String.valueOf(cipherTextResultMask));
    }


	//[cipherTextList, n, cipherThreshold, mask]
	public static void main(String[] str) {

		Paillier_eVerify paillier = new Paillier_eVerify(str);

		String[] ciphertextList = str[0].split("\\,");

		BigInteger cipherThreshold = new BigInteger(str[2]);

		BigInteger mask = new BigInteger(str[3]);

		BigInteger ciphertextSum = paillier.ciphertextSummation(ciphertextList);

		BigInteger cipherTextResult = (ciphertextSum.multiply(cipherThreshold.modInverse(nsquare))).mod(nsquare);

		BigInteger cipherTextResultMask = cipherTextResult.modPow(mask, nsquare);

		paillier.returnValue(cipherTextResultMask);

	}
}
