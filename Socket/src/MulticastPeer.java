import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class MulticastPeer extends Thread {
	private static int idCounter = 0;
    private MulticastSocket sock;
    private int port;    
    private int id;
    private String user;
    private InetAddress group;
    private String ip;
    private Thread thread;
    private Boolean keepAlive = true;
    private static ArrayList<String> userList = new ArrayList<String>();
    
    //class constructor for MulticastPeer
    public MulticastPeer(String group, int port, String user){
    	// the group proper will be set by InetAddress.getByName using the string, later
    	this.ip = group;
    	this.port = port;
    	this.user = user;
    }
    
    // connects user to the group and adds them to the list of connected users
    public void connect() {
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
    
    public void send(Message message) {
    	if(sock != null) {
            try {
                byte[] msg = message.toString().getBytes();
                DatagramPacket messageOut = new DatagramPacket(msg, msg.length, group, port);
                System.out.printf("<< Peer %s sending: %s\n", id, message.toString());
                sock.send(messageOut);                
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            }
    	}
    	else {
    		System.out.printf("Peer %s not connected!\n", id);
    	}
    }
    
    private Message listenToGroup() {
    	// get messages from others in the same group
    	if(sock != null) {
	    	try {
		    	byte[] buffer = new byte[1000];
		    	DatagramPacket messageIn =	new DatagramPacket(buffer, buffer.length);
		    	sock.receive(messageIn);
		    	//String received = new String(messageIn.getData());
		    	//System.out.printf("Received: %s.\n", received);
		    	Message received = new Message();
		    	received.packetToMessageMap(messageIn);
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
    		Message received = listenToGroup();
    		if(received != null) {
    			System.out.printf(">> Peer %s said: %s\n", received.getUsername(), new String(received.getMessageBody()));
    		}
    		
    	}
    	System.out.println("Quitting.");
    }
    
}

