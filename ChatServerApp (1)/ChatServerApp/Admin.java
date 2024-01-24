import java.awt.*;
/**
 * The Admin class represents an administrator of a chat server, who can open and close chat rooms.
 * It is an active class that implements the Runnable interface, and can be run as a thread.
 */

public class Admin implements Runnable {

    private static int sleepScale = 100;
    private String name;
    private Admin admin;
    private ChatServer chatServer;
    private int numOfActions;  //Could be either openning or closing a chatroom

    /**
     * Constructs an Admin object with the given name and initializes other attributes to default values.
     *
     * @param name the name of the admin
     */
    public Admin(String name) {
        this.name = name;
        this.chatServer = null;
        this.admin = null;
        this.numOfActions = 15;
        // Set the initial value of class variables
    }
    /**
     * Assigns a ChatServer object to this Admin object.
     *
     * @param chatServer the ChatServer object to be assigned
     */
    public void assignServer(ChatServer chatServer) {
        // Store given Chat Server in Class Attribute
        this.chatServer = chatServer;
    }

    /**
     * Runs the Admin thread by opening and closing chat rooms on the assigned ChatServer.
     * This method will be executed when the thread is started.
     */
    @Override

    public void run() {
        while (numOfActions > 0) {
            try {
                chatServer.open();
                int chatRoomID = (int) Math.random() * 10;
                long pauseTime = (long) (Math.random() * 10000 + 100000); // generate random pause time between 1000ms and 2000ms


                Thread.sleep(sleepScale);
                chatServer.openChatRoom(chatRoomID);
                Thread.sleep(sleepScale);
                chatServer.closeChatRoom(chatRoomID);
                numOfActions--;
                chatServer.close();
            } catch (InterruptedException ex) {
                System.out.println("Thread Interrupted");
            }
        }
    }

}