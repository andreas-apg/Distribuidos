import java.util.HashMap;
import java.util.Map;

/* this class is used to store the info for
 * the messages that will be sent in the
 * program. The messages have three parts:
 * type - the type of message;
 * username - the name of the sender;
 * messageBody - the message contents;
 * signature - the signature for the message,
 * generated using DSA.
 */
public class Message {
	Map<String, byte[]> messageMap = new HashMap<String, byte[]>();
	
	// constructor for message class.
	public Message(String type, String username, String body, byte[] signature) {
		messageMap.put("type", type.getBytes());
		messageMap.put("username", username.getBytes());
		messageMap.put("messageBody", body.getBytes());
		messageMap.put("signature", signature);
	}
	
	public String getType() {
		return new String(messageMap.get("type"));
	}
	
	public String getUsername() {
		return new String(messageMap.get("username"));
	}
	
	public byte[] getBody() {
		return messageMap.get("messageBody");
	}
	
	public byte[] getSignature() {
		return messageMap.get("signature");
	}
	
}
