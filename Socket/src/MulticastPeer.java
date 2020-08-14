import java.net.*;
import java.security.interfaces.DSAPrivateKey;
import java.io.*;

public class MulticastPeer extends Thread {
	private static int idCounter = 0;
    private MulticastSocket sock;
    public int port;    
    public int id;
    public String user;
    public InetAddress group;
    public String ip;
    private Thread thread;
    private Boolean keepAlive = true;
    
    public void connect(String ip, int port, String user) {
    	this.ip = ip;
    	this.port = port;
    	this.user = user;
    	idCounter++;
    	id = idCounter;
        try {
        	group = InetAddress.getByName(ip);
		    sock = new MulticastSocket(port);
		    sock.joinGroup(group);
		    System.out.printf("Peer %s connected to IP %s, port %s.\n", id, ip, port);
		    System.out.printf("Number of peers: %s\n", numberOnline());
		    this.start();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
    
    public void send(String message) {
    	if(sock != null) {
            try {
                byte[] msg = message.getBytes();
                DatagramPacket messageOut = new DatagramPacket(msg, msg.length, group, port);
                sock.send(messageOut);
                System.out.printf("<< Peer %s sending: %s\n", id, message);
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            }
    	}
    	else {
    		System.out.printf("Peer %s not connected!\n", id);
    	}
    }
    
    private String listenToGroup() {
    	// get messages from others in the same group
    	if(sock != null) {
	    	try {
		    	byte[] buffer = new byte[1000];
		    	DatagramPacket messageIn =	new DatagramPacket(buffer, buffer.length);
		    	sock.receive(messageIn);
		    	String received = new String(messageIn.getData());
		    	//System.out.printf("Received: %s.\n", received);
		    	return received;
		    }catch (SocketException e){
		    	System.out.printf("Socket %s: %s.\n", id ,e.getMessage());
		    	keepAlive = false;
		    }catch (IOException e){
		    	System.out.println("IO: " + e.getMessage());
		    }/*finally {
		    	if(sock != null) sock.close();
		    }*/
    	}
    	return null;
    }
    
    public int numberOnline() {
    	return idCounter;
    }
    
    public void start() {
    	if(thread == null) {
		    thread = new Thread(this, user);
		    System.out.printf("Peer %s: starting thread...\n", id);
		    thread.start();
    	}
    }
    public void close() {
    	System.out.printf("Peer %s out.\n", id);
    	keepAlive = false;
    	sock.close();
    }
    
    public void run(){    	
    	while(keepAlive) {
    		String received = listenToGroup();
    		if(received != null) {
    			System.out.printf(">> Peer %s received: %s\n", id, received);
    		}
    		
    	}
    	System.out.println("Quitting.");
    }
    
}

