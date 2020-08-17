/* this class was made to simplify
 * the creation of the user list.
 */

public class User {
	private String username;
	private byte[] publicKey;
	private int reputation;
	
	public User(String username, byte[] publicKey, int reputation) {
		this.username = username;
		this.publicKey = publicKey;
		this.setReputation(reputation);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

}
