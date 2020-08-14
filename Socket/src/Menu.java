import java.util.Scanner;


public class Menu{
	/*public static void main(String args[]){
		Scanner keyboard = new Scanner(System.in);
		String option = keyboard.nextLine();
		System.out.println(option);
	}
	*/
	public static void main(String args[]){
		MulticastPeer p1 = new MulticastPeer();
		MulticastPeer p2 = new MulticastPeer();
		MulticastPeer p3 = new MulticastPeer();
		p1.connect("227.0.0.10", 6543, "Joe");
		p1.send("Test1.");
		p2.connect("227.0.0.10", 6543, "Bob");
		p1.send("Test2.");
		p3.connect("227.0.0.10", 6543, "Who");
		p1.send("Test3.");
	}
}

