import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Unicast extends Thread{
	private int port;
	private boolean keepAlive;
	private DatagramSocket sock;
	private Thread thread;
	
	// constructor for Unicast class.
	public Unicast() {
		try {
			sock = new DatagramSocket();
			// using the local port for this.
			port = sock.getLocalPort();
		} catch (SocketException e){
	    	System.out.println("Socket error in unicast " +  e.getMessage());
	    	keepAlive = false;
	    }
	}
	
	// starts the class' thread.
    public void start() {
    	if(thread == null) {
		    thread = new Thread(this, "unicast");
		    System.out.println("Starting unicast thread...");
		    thread.start();
    	}
    }
	
    // runs the listen() method
	public void run() {
		keepAlive = true;
		listen();		
	}
	
	// this method keeps listening for any incoming messages.
	private void listen() {
		try {
			while(keepAlive) {
				byte[] buffer = new byte[1000];
				DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
				sock.receive(messageIn);
				
				Message received = new Message();
				received.packetToMessageMap(messageIn);
				if(received.getType().equals("hail")){
					System.out.printf("Adding peer %s's info to the user list.\n", received.getUsername());
					/* this adds the user who just multicasted their public
					* key to the user list.
					*/
					MulticastPeer.addPeer(received.getUsername(), received.getPublicKey(), received.getUnicastPort(), 10);
					// lists all connected peers.
					MulticastPeer.peerList();
				}
			}
		}
		catch(IOException e) {
			System.out.println("IO exception at unicast listen: " + e.getMessage());
		}
		finally {
			if(sock != null) {
				sock.close();
			}
		}
	}
	
	public void send(Message message, String destinationHostName, int destinationHostPort) {
		DatagramSocket sock = null;
		try {
			System.out.printf("Sending key to port: %s@%s\n", destinationHostPort, destinationHostName);
			sock = new DatagramSocket();
			byte[] msg = message.toString().getBytes();
			InetAddress inetAddress = InetAddress.getByName(destinationHostName);
			DatagramPacket request = new DatagramPacket(msg, msg.length, inetAddress, destinationHostPort);
			sock.send(request);			
		}
		catch(IOException e) {
			System.out.println("IO exception at unicast listen: " + e.getMessage());
		}
		finally {
			if(sock != null) {
				sock.close();
			}
		}
	}
	
	public void close() {
		keepAlive = false;
		System.out.println("Closing unicast server.");
		sock.close();
	}

	public int getPort() {
		return port;
	}
}

