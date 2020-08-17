import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class MulticastPeer extends Thread {
	private static int idCounter = 0;
    private MulticastSocket sock;
    private int port;    
    private int id;
    private User user;
    private InetAddress group;
    private String ip;
    private Thread thread;
    private Boolean keepAlive = true;
    private ArrayList<User> userList = new ArrayList<User>();
    
    //class constructor for MulticastPeer
    public MulticastPeer(String group, int port, String user, byte[] publicKey){
    	// the group proper will be set by InetAddress.getByName using the string, later
    	this.ip = group;
    	this.port = port;
    	this.user = new User(user, publicKey, 10);
    }
    
    // connects user to the group and adds them to the list of connected users
    public void connect() {
    	idCounter++;
    	id = idCounter;
        try {
        	group = InetAddress.getByName(ip);
		    sock = new MulticastSocket(port);
		    sock.joinGroup(group);
		    System.out.printf("Peer %s connected to IP %s, port %s.\n", user.getUsername(), ip, port);
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
                //System.out.printf("<< Peer %s sending: %s\n", id, message.toString());
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
    
    public void start() {
    	if(thread == null) {
		    thread = new Thread(this, user.getUsername());
		    System.out.printf("Peer %s: starting thread...\n", user.getUsername());
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
    		// no need to treat messages said by the own user.
    		if(received != null && !received.getUsername().equals(user.getUsername())) {
    			if(received.getType().equals("input")) {
    				System.out.printf(">> Peer %s said: %s\n", received.getUsername(), new String(received.getMessageBody()));
    			}
    			if(received.getType().equals("hail")) {
    				System.out.printf("Peer %s has joined the group.\n", received.getUsername());
    				User newUser = new User(received.getUsername(), Message.charset.encode(received.getMessageBody()).array(), 10);
    				userList.add(newUser);
    			    System.out.printf("Number of peers: %s. Known peers: \n", userList.size() + 1);
    			    for(User i : userList) {
    			    	if(!i.getUsername().equals(user.getUsername())) {
    			    		System.out.printf("%s: %s\n", i.getUsername(), i.getReputation());
    			    	}
    			    }
    			}
    			else if(received.getType().equals("quit")) {
    				System.out.printf("Peer %s quit the group.", received.getUsername());
    			}
    		}
    		
    	}
    	System.out.println("Quitting.");
    }
    
}

