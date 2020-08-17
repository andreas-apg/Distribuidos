import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.Base64;


//https://docs.oracle.com/javase/tutorial/security/apisign/step2

public class Sign {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private Signature rsa;
	// constructor for the Sign class
	public Sign() {
		try {
			/* initializing the key pair generator using
			 * key length of 1024 bits and the SHA1PRNG
			 * algorithm as source of randomness. RSA
			 * is being used here because it works for
			 * signature AND encryption both.
			 */
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(1024, random);
			
			/* generating the key pair to be stored in
			 * the privateKey and publicKey variables.
			 */
			KeyPair pair = keyGen.generateKeyPair();
			privateKey = pair.getPrivate();
			publicKey = pair.getPublic();
			
			/* getting the Signature object used for generating or
			 * verifying signatures using the RSA algorithm.
			 * SHA1withDSA specifies RSA with SHA-1 message digest
			 * algorithm, which ensures the message's integrity.
			 */
			rsa = Signature.getInstance("SHA1withRSA");
			
			/* before the signature object can be used, it must be
			 * initialized with a private key.
			 */
			rsa.initSign(privateKey);
		}catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
	}
	
	// this method signs a message.
	public String signDown(String message) {
		try {
			/* the signature will use the rsa object created
			 * when the class was instanced.
			 */
			Signature signature = rsa;
			
			/* the message is supplied to the signature object
			 * using the "update" method.
			 */
			signature.update(message.getBytes());
	
			
			/* the signature is obtained from the signature
			 * object using the "sign" method.
			 */
			byte[] signByte = signature.sign();
			return Base64.getEncoder().encodeToString(signByte);

		} catch (SignatureException e) {
			System.err.println("Caught signature exception " + e.toString());
		}
		return null;
	}

	/* this method verifies if the signature
	 * matches that of the public key.
	 */
	public static Boolean verify(String message, PublicKey publicKey, byte[] signature) {
		try {
			/* to verify a signature, another instance of the signature
			 * class must be created using the same algorithm that was
			 * used to create the signature.
			 */
			Signature verification = Signature.getInstance("SHA1withRSA");
			
			/* the object is then initialized using the public key
			 * allegedly associated with the message and its signature.
			 */
			verification.initVerify(publicKey);
			
			/* next, that object must also receive the message associated
			 * with the signature.
			 */
			verification.update(message.getBytes());
			
			/* the "verify" method returns true if the provided
			 * public key matches with the signature.
			 */
			return verification.verify(signature);
		} 
		catch (NoSuchAlgorithmException e) {
		} catch (SignatureException e) {
			System.err.println("Caught signature exception " + e.toString());
		} catch (InvalidKeyException e) {
			System.err.println("Caught invalid key exception " + e.toString());
		}
		return null;		
	}
	
	/* this method encrypts a message using RSA encryption
	* using the private key. 
	*/
	public byte[] encode(String message) {
		try {
		/* initializing the cipher for encrypting the message
		* using the RSA algorithm. 
		*/
		Cipher cipher = Cipher.getInstance("RSA");
		
		/* the cipher is initialized for encryption using the 
		 * instanced private key. 
		 */
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		/* The message is encrypted by adding it to the cipher
		 * using the "update" method and then calling "doFinal".
		 */
		cipher.update(message.getBytes());		
		return cipher.doFinal();		
		} 
		catch (BadPaddingException e) {
			System.err.println("Caught padding exception " + e.toString());
		} 
		catch (IllegalBlockSizeException e) {
		}
		catch (InvalidKeyException e) {
			System.err.println("Caught invalid key exception " + e.toString());
		} 
		catch (NoSuchAlgorithmException e) {
			System.err.println("Caught encryption algorithm exception " + e.toString());
		} 
		catch (NoSuchPaddingException e) {
			System.err.println("Caught padding exception " + e.toString());
		}
		return null;
	}
	
	/* this method decodes a message that was encrypted
	 * with a given private key using its paired public key.
	 */
	public byte[] decode(byte[] cryptoMessage, PublicKey publicKey) {
		try {
		/* initializing the cipher for decrypting the message
		* using the RSA algorithm. 
		*/
		Cipher cipher = Cipher.getInstance("RSA");
		
		//the cipher is initialized for decryption using the passed public key.
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		/* The message is encrypted by adding it to the cipher
		 * using the "update" method and then calling "doFinal".
		 */	
		return cipher.doFinal(cryptoMessage);
		} catch (BadPaddingException e) {
			System.err.println("Caught padding exception " + e.toString());
		} catch (IllegalBlockSizeException e) {
		}catch (InvalidKeyException e) {
			System.err.println("Caught invalid key exception " + e.toString());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Caught encryption algorithm exception " + e.toString());
		} catch (NoSuchPaddingException e) {
			System.err.println("Caught padding exception " + e.toString());
		}
		return null;
	}
	
    // Converts the PublicKey object into a string.
    public static String toString(PublicKey pub) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(pub, X509EncodedKeySpec.class);
        return Base64.getEncoder().encodeToString(spec.getEncoded());
    }

    // Converts the the string with the public key back into PublicKey object.
    public static PublicKey toPublicKey (String pubKey_str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(pubKey_str);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (PublicKey) keyFactory.generatePublic(keySpec);
    }
	
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
}
