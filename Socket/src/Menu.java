import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Menu{
	/*public static void main(String args[]){
		Scanner keyboard = new Scanner(System.in);
		String option = keyboard.nextLine();
		System.out.println(option);
	}
	*/
	public static void main(String args[]){
		Sign signature = new Sign();
		Sign signature2 = new Sign();
		Scanner keyboard = new Scanner(System.in);
		/*
		System.out.print("Username: ");
		String user = keyboard.nextLine();
		MulticastPeer p1 = new MulticastPeer("227.0.0.10", 6543, user);
		p1.connect();
		String text = "";
		while(!text.toLowerCase().equals("quit")) {
			System.out.printf("<< %s says: %s", user, text);
			text = keyboard.nextLine();				
			if(text.toLowerCase() != "quit") {
				p1.send(new Message("input", user, text, signature.signDown(text)));
			}
		}
		p1.close();*/
		
		System.out.print("What");
		String message = keyboard.nextLine();
		System.out.println("Message: " + new String(message.getBytes()));
		byte[] sign = signature.signDown(message);
		byte[] sign2 = signature2.signDown(message);
		System.out.println("Signature: " + new String(signature.signDown(message)));
		Message msg = new Message("input", "Me", message, sign);
		//System.out.println("Signature 1 on object 1 verifies: " + signature.verify(message, signature.getPublicKey(), sign));
		//System.out.println("Signature 2 on object 2 verifies: " + signature.verify(message, signature.getPublicKey(), sign2));
		//System.out.println("Signature 2 on object 1 verifies: " + signature.verify(message, signature2.getPublicKey(), sign));
		//System.out.println("Signature 2 on object 2 verifies: " + signature.verify(message, signature2.getPublicKey(), sign2));
		String mst = msg.toString();
		try {
			System.out.println(new String(sign, "ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mst);
		//System.out.println(msg.getType());
		//System.out.println(new String(msg.getMessageBody()));
		//System.out.println(msg.getUsername());
		//System.out.println(new String(msg.getSignature()));
		//System.out.println(msg.toString());
		System.out.println("Signature 1 on object 1 verifies: " + signature.verify(msg.getMessageBody(), signature.getPublicKey(), sign));
		System.out.println("Signature 1 on object 1 verifies: " + signature.verify(msg.getMessageBody(), signature.getPublicKey(), msg.getSignature()));
		keyboard.close();
	}
}
