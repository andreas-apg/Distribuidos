import java.net.DatagramPacket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/* this class is used to store the info for
 * the messages that will be sent in the
 * program. The messages have three parts:
 * >type - the type of message. Possible values:
 *  "input", "pubKey", "hail", "quit", "grade";
 * >username - the name of the sender;
 * >messageBody - the message contents;
 * >signature - the signature for the message,
 * generated using DSA.
 */
public class Message {
	Map<String, String> messageMap = new HashMap<String, String>();
	public final static Charset charset = StandardCharsets.ISO_8859_1;
	
	// constructor for message class.
	public Message(String type, String username, String body, String signature) {
		messageMap.put("type", type);
		messageMap.put("username", username);
		messageMap.put("messageBody", body);
		
		/* it is needed to specify the charset as ISO_8859_1
		 * to properly convert the signature into a string.
		 * charset.decode is used to guarantee the typecasting
		 * in the other way.
		 * Source: https://www.baeldung.com/java-string-to-byte-array
		 */
		messageMap.put("signature", signature);
	}
	
	// constructor for messages that do not need verification.
	public Message(String type, String username, String body) {
		messageMap.put("type", type);
		messageMap.put("username", username);
		messageMap.put("messageBody", body);
	}
	
	// constructor for greeting messages
	public Message(String type, String username, int unicastPort, String publicKey) {
		messageMap.put("type", type);
		messageMap.put("username", username);
		messageMap.put("unicastPort", String.valueOf(unicastPort));
		messageMap.put("publicKey", publicKey);
	}
	// empty constructor used for received messages.
	public Message() {
		
	}
	
	/* method used to convert DatagramPackets obtained from
	 * UDP communication into a message object.
	 */
	public void packetToMessageMap(DatagramPacket messageIn) {
		// removes leading and trailing whitespace
		String contents = new String(messageIn.getData()).trim();
		
		// removes the curly brackets	
		contents = contents.substring(1, contents.length()-1);
		
		// splits the string to create key-value pairs
		String[] keyValuePairs = contents.split(",");
		
		/* this loop adds the elements from the
		 * message to the message hash map.
		 */
		for(String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			if(entry.length > 1) {
				messageMap.put(entry[0].trim(), entry[1].trim());
			}
		}
	}
	
	/* method used to verify the message using
	 * its signature along with that peer's public key.
	 */
	public boolean verifyMessage(String rawPublicKey) {
		try {			
			/* the keyFactory class is used re-obtain the public
			 * key that was converted to a byte[]. However, it
			 * is needed to decode it with the correct charset:
			 * ISO_8859_1 and feed that to an X509EncodedKeySpec
			 * class, first.
			 */
			PublicKey publicKey = Sign.toPublicKey(rawPublicKey);
			
			/* the verify method made for the Sign class will be used
			 * to do the verification proper
			 */
			byte[] signature = Base64.getDecoder().decode((this.getSignature()));
			return Sign.verify(this.getMessageBody(), publicKey, signature);
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("KeyFactory algorithm error: " + e.getMessage());
	    } catch (InvalidKeySpecException e) {
	    	System.out.println("KeyFactory key specification error: " + e.getMessage());
		}
		return false;
	}
	
	public String getType() {
		return new String(messageMap.get("type"));
	}
	
	public String getUsername() {
		return new String(messageMap.get("username"));
	}
	
	public String getMessageBody() {
		return messageMap.get("messageBody");
	}
	
	public String getUnicastPort() {
		return messageMap.get("unicastPort");
	}
	
	public String getPublicKey() {
		return messageMap.get("publicKey");
	}
	
	public byte[] getSignature() {
		/* using ISO_8859_1 to encode the string back
		 * into the original byte[] format used for
		 * verification.
		 */
		return charset.encode(messageMap.get("signature")).array();
	}
	
	public Map<String, String> getMessageMap(){
		return messageMap;
	}
	
	public String toString() {
		return messageMap.toString();
	}
	
}
