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
		Scanner keyboard = new Scanner(System.in);/*
		System.out.print("Username: ");
		String user = keyboard.nextLine();
		MulticastPeer p1 = new MulticastPeer("227.0.0.10", 6543, user);
		p1.connect();
		String message = "";
		while(!message.toLowerCase().equals("quit")) {
			System.out.print("Message: ");
			message = keyboard.nextLine();			
			if(message.toLowerCase() != "quit") {
				p1.send(message);
			}
		}
		p1.close();*/
		System.out.print("Message: ");
		String message = keyboard.nextLine();
		System.out.println("Message: " + new String(message.getBytes()));
		byte[] sign = signature.signDown(message);
		byte[] sign2 = signature2.signDown(message);
		System.out.println("Signature: " + signature.signDown(message));
		Message msg = new Message("text", "Me", message, sign);
		System.out.println("Signature 1 on object 1 verifies: " + signature.verify(message, signature.getPublicKey(), sign));
		System.out.println("Signature 2 on object 2 verifies: " + signature.verify(message, signature.getPublicKey(), sign2));
		System.out.println("Signature 2 on object 1 verifies: " + signature.verify(message, signature2.getPublicKey(), sign));
		System.out.println("Signature 2 on object 2 verifies: " + signature.verify(message, signature2.getPublicKey(), sign2));
		System.out.println(msg.getType());
		System.out.println(new String(msg.getBody()));
		System.out.println(msg.getUsername());
		System.out.println(msg.getSignature());
		keyboard.close();
	}
}

