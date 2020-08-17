import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/* this class is used to store the info for
 * the messages that will be sent in the
 * program. The messages have three parts:
 * >type - the type of message. Possible values:
 *  "input", "pubKey", "hail", "bye";
 * >username - the name of the sender;
 * >messageBody - the message contents;
 * >signature - the signature for the message,
 * generated using DSA.
 */
public class Message {
	Map<String, String> messageMap = new HashMap<String, String>();
	
	// constructor for message class.
	public Message(String type, String username, String body, byte[] signature) {
		messageMap.put("type", type);
		messageMap.put("username", username);
		messageMap.put("messageBody", body);
		Charset charset = StandardCharsets.US_ASCII;
		messageMap.put("signature", charset.decode(ByteBuffer.wrap(signature)).toString());
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
			System.out.println(entry[0]);
			System.out.println(entry[1]);
			messageMap.put(entry[0].trim(), entry[1].trim());
		}
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
	
	public byte[] getSignature() {
		Charset charset = StandardCharsets.US_ASCII;
		return charset.encode(messageMap.get("signature")).array();
	}
	
	public Map<String, String> getMessageMap(){
		return messageMap;
	}
	
	public String toString() {
		return messageMap.toString();
	}
	
}
