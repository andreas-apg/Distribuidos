import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class MulticastPeer extends Thread {
	private static int idCounter = 0;
    private MulticastSocket sock;
    private int port;    
    private int id;
    private static User user;
    private InetAddress group;
    private String ip;
    private Thread thread;
    private Boolean keepAlive = true;
    private static ArrayList<User> userList = new ArrayList<User>();
    private Unicast unicast = new Unicast();
    
    //class constructor for MulticastPeer
    public MulticastPeer(String group, int port, String user, String publicKey){
    	// the group proper will be set by InetAddress.getByName using the string, later
    	this.ip = group;
    	this.port = port;
    	MulticastPeer.user = new User(user, publicKey, 10);
    }
    
    // connects user to the group and adds them to the list of connected users
    public void connect() {
    	idCounter++;
    	id = idCounter;
        try {
        	// starts the multicast thread
        	group = InetAddress.getByName(ip);
		    sock = new MulticastSocket(port);
		    sock.joinGroup(group);
		    System.out.printf("Peer %s connected to IP %s, port %s.\n", user.getUsername(), ip, port);
		    this.start();
		    
		    // starts the unicast thread
		    unicast.start();
		    user.setUnicastPort(unicast.getPort());
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
    	unicast.close();
    	sock.close();
    }
    
    public static void peerList() {
	    System.out.printf("Number of other peers: %s. Known peers: \n", userList.size());
	    for(User i : userList) {
	    	if(!i.getUsername().equals(user.getUsername())) {
	    		System.out.printf("%s, rep: %s\n", i.getUsername(), i.getReputation());
	    		//System.out.println(i.getPublicKey());
	    	}
	    }
    }
    
    public static void addPeer(String username, String publicKey, String unicastPort, int reputation) {
    	User newUser = new User(username, publicKey, Integer.parseInt(unicastPort), reputation);
		userList.add(newUser);
    }
    
    // method that continuously runs in the thread.
    public void run(){    	
    	while(keepAlive) {
    		Message received = listenToGroup();
    		/* no need to treat messages from the the own user,
    		 * thus the check if the username in the package
    		 * is the same as the instanced one.
    		 */
    		if(received != null && !received.getUsername().equals(user.getUsername())) {
    			/* if message type is "input", its contents
    			 * are the text string sent by the user, along
    			 * with the digital signature generated for it.
    			 * source: https://docs.oracle.com/javase/tutorial/security/apisign/vstep2.html
    			 */
    			if(received.getType().equals("input")) {
    				String rawPublicKey = null;
    				for(User i : userList) {
    					if(received.getUsername().equals(i.getUsername())) {
    						//System.out.println(received.getUsername() + "  " + i.getUsername());
    						//System.out.println(received.getUsername().equals(i.getUsername()));
    						rawPublicKey = i.getPublicKey();
    						break;
    					}
    					//System.out.println(rawPublicKey);
    				}
    				System.out.printf(">> Peer %s said: %s\n", received.getUsername(), new String(received.getMessageBody()));
    				System.out.printf("The message verifies: %s.\n", received.verifyMessage(rawPublicKey));
    			}
    			/* if message type is "hail", the it contains
    			 * that peer's public key and unicast port.
    			 */
    			else if(received.getType().equals("hail")) {
    				System.out.printf("Peer %s has joined the group.\n", received.getUsername());
    				System.out.printf("Received key from %s. Their port: %s\n", received.getUsername(), received.getUnicastPort());
    				addPeer(received.getUsername(), received.getPublicKey(), received.getUnicastPort(), 10);
    				/* we will send to the peer who sent the message this one's
					* username, unicast port and public key 
					* */
    				unicast.send(new Message("hail", user.getUsername(), user.getUnicastPort(), user.getPublicKey()), "127.0.0.1", Integer.parseInt(received.getUnicastPort()));
    				peerList();
    			}
    			/* if the message type is "quit", that peer
    			 * is quitting the group and will accordingly
    			 * be removed from the local user list.
    			 */
    			else if(received.getType().equals("quit")) {
    				System.out.printf("Peer %s quit the group.\n", received.getUsername());
    				// removes the user from the userList when they quit.
    				for(User i : userList) {
    					if(i.getUsername().equals(received.getUsername())) {
    						userList.remove(i);
    						break;
    					}
    				}
    				
    				peerList();
    			}
    		}
    		
    	}
    	System.out.println("Quitting.");
    }
    
    public Unicast getUnicast() {
    	return this.unicast;
    }
    
}

