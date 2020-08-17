import java.security.GeneralSecurityException;
import java.util.Scanner;


public class Menu{
	/*public static void main(String args[]){
		Scanner keyboard = new Scanner(System.in);
		String option = keyboard.nextLine();
		System.out.println(option);
	}
	*/
	public static void main(String args[]){
		Sign signature = new Sign();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Username: ");
		String user = keyboard.nextLine();
		//byte[] encodedPublicKey = signature.getPublicKey().getEncoded();
		//MulticastPeer p1 = new MulticastPeer("227.0.0.10", 6543, user, encodedPublicKey);
		MulticastPeer p1 = null;
		try {
			p1 = new MulticastPeer("227.0.0.10", 6543, user, Sign.toString(signature.getPublicKey()));
			p1.connect();
		} catch (GeneralSecurityException e2) {
            System.out.println("Menu GeneralSecurityException: " + e2.getMessage());
        }
		
		/* sending the public key in multicast. The key is converted
		 * to its keySpec equivalent so it can be stored in a string
		 * without being corrupted.
		 */
		try {
			p1.send(new Message("hail", user,  p1.getUnicast().getPort(), Sign.toString(signature.getPublicKey())));
		} catch (GeneralSecurityException e1) {
            System.out.println("Hail menu GeneralSecurityException: " + e1.getMessage());
        }
		String text = "";
		while(!text.toLowerCase().equals("quit")) {
			System.out.println("");
			//System.out.printf("<< %s says: ",user, text);		
			text = keyboard.nextLine();			
			if(text.equals("peer")) {
				MulticastPeer.peerList();
			}
			else if(text.equals("report")) {
				if(MulticastPeer.getLatestMessage()!= null) {
					if(MulticastPeer.getLatestMessage().getType().equals("input")) {
						p1.send(new Message("grade", p1.getUser().getUsername(), MulticastPeer.getLatestMessage().getUsername()));	
	    				for(User i : p1.getUserList()) {
	    					if(MulticastPeer.getLatestMessage().getUsername().equals(i.getUsername())) {
	    						i.setReputation(i.getReputation() - 1);
	    						break;
	    					}		
	    				}
	    				System.out.printf("<< You have reported %s.\n", MulticastPeer.getLatestMessage().getUsername());
	    				MulticastPeer.peerList();
					}
				}
			}
			else if(!text.toLowerCase().equals("quit") && !text.isEmpty()) {
				p1.send(new Message("input", user, text, signature.signDown(text)));				
			}
	
		}
		try {
			p1.send(new Message("quit", user, Sign.toString(signature.getPublicKey())));
		} catch (GeneralSecurityException e) {
            System.out.println("Quit GeneralSecurityException: " + e.getMessage());
        }
		p1.close();
		keyboard.close();
	}
}

