/* this class was made to simplify
 * the creation of the user list.
 */

public class User {
	private String username;
	private String publicKey;
	private int unicastPort;
	private int reputation;
	
	public User(String username, String publicKey, int reputation) {
		this.username = username;
		this.publicKey = publicKey;
		this.setReputation(reputation);
	}
	
	public User(String username, String publicKey, int unicastPort, int reputation) {
		this.username = username;
		this.publicKey = publicKey;
		this.unicastPort = unicastPort;
		this.setReputation(reputation);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public int getUnicastPort() {
		return unicastPort;
	}
	
	public void setUnicastPort(int unicastPort) {
		this.unicastPort = unicastPort;
	}

}
