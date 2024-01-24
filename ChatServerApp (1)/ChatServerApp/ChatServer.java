import java.util.ArrayList;
import java.util.List;
/**

 ChatServer class represents a server for chat rooms that allows users to chat with each other.

 It contains a list of chat rooms and users, an admin who can control the server, and various methods to manage the chat rooms and users.

 @author [Elias Hassan]

 @version 1.0


 */
//passive
// Is this a passive or active class?
public class ChatServer {

    private ArrayList<ChatRoom> rooms;
    private List<User> users;
    private Admin admin;
    private int capacity;
    private boolean isOpen;
    private int numOfRooms;
    /**

     Constructor for the ChatServer class.
     @param capacity The maximum number of users that can be in the server at one time
     @param numOfRooms The number of chat rooms in the server
     @param admin The administrator of the server
     */
    public ChatServer(int capacity, int numOfRooms, Admin admin) {
        this.capacity = capacity;
        this.isOpen = false;
        this.users = new ArrayList<User>();
        this.rooms = new ArrayList<ChatRoom>(2);
        for (int i = 0; i < numOfRooms; i++) {
            rooms.add(new ChatRoom(i, capacity));
        }
        this.numOfRooms = numOfRooms;
        this.admin = admin;
        admin.assignServer(this);
    }
    /**

     Opens the chat server for users to join.
     */
    // Consider if this should be run asynchronously. ADMIN
    public synchronized void open() {
        // Code to open the Chat Room.
        if (!isOpen) {
            isOpen = true;
            System.out.println("Chat Server is Opened.");
            notifyAll();
        }
    }
    /**

     Closes the chat server, if there are no users currently in the server.
     */
    // Consider if this should be run asynchronously. ADMIN
    public synchronized void close() {
        System.out.println("Chat Server is being Closed.");
        if (isOpen && getNumberOfUsers() == 0) {
            isOpen = false;
            System.out.println("Chat Server is Closed.");
            notifyAll();
        }
    }
    /**

     Allows a user to join the chat server, if the server is not at capacity.
     @param user The user to join the server
     @return true if the user is able to join the server, false otherwise
     */
    // Consider if this should be run asynchronously.
    public synchronized boolean join(User user) {
        if (!users.contains(user) && getNumberOfUsers() >= capacity) {
            System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ").");
            return false;
        } else {
            users.add(user);

            System.out.println("User " + user.getID() + " admitted to Chat Server (" + user.getWantToChat() + ")." + users.size() + " " + rooms.size() + numOfRooms);
            return true;
        }
    }
    /**

     Allows a user to leave the chat server.
     @param user The user to leave the server
     */
    // Consider if this should be run asynchronously.
    public void leave(User user) {
        // Code for a User to leave the Chat Server.
        if (users.contains(user)) {
            users.remove(user);
            System.out.println("User " + user.getID() + " left the Chat Server." + getNumberOfUsers());
        } else {
            System.out.println("Could not remove User " + user.getID() + " as is not in the Chat Server." + rooms.size());
        }
    }
    /**

     Opens a chat room in the server.
     @param chatRoomID The ID of the chat room to open
     */
    public void openChatRoom(int chatRoomID) {
        // Code to open Chat Room.
        if (rooms.size() < numOfRooms && isRoomOpen(chatRoomID)) {

            System.out.println("Chat Room " + chatRoomID + " is OPEN");

        } else {
            isOpen = true;
            getMain().setIsOpen(true);
            System.out.println("couldnt open chat room" + rooms.get(0).getIsOpen());
        }

    }
    /**

     Closes a chat room in the server.
     @param chatRoomID The ID of the chat room to close
     @throws InterruptedException if there are still users in the chat room
     */
    public synchronized void closeChatRoom(int chatRoomID) throws InterruptedException {
        // Code to close Chat Room.
        while (isRoomOpen(chatRoomID) && getNumberOfUsers() > 0) {
            wait();
        }
        getMain().setIsOpen(false);
    }
    /**

     Allows a user to enter a chat room in the server.
     @param user The user to enter the chat room
     @param chatRoomID The ID of the chat room to enter
     @return true if the user is able to enter the chat room, false otherwise
     */
    public synchronized boolean enterRoom(User user, int chatRoomID) {
        ChatRoom chatRoom = new ChatRoom(chatRoomID, capacity);
        boolean entered = chatRoom.enterRoom(user);
        if (entered ) {
            chatRoom.enterRoom(user);
            users.add(user);
            System.out.println("User " + user.getID() + " has entered Room " + chatRoomID);
        }
        return true;
    }
    /**

     Allows a user to leave a chat room in the server.
     @param user The user to leave the chat room
     @param chatRoomID The ID of the chat room to leave
     */
    public synchronized void leaveRoom(User user, int chatRoomID) {
        // Code to allow user to leave Chat Room.
        if (isOpen)
            rooms.remove(this);
        notifyAll();
    }
    /**returns number of rooms
     * @return number of rooms.
     */
    public int getNumberOfRooms() {
        return rooms.size();
    }
    /**
     * Returns whether the specified chat room is currently open.
     *
     * @param chatRoomID the ID of the chat room to check
     * @return true if the chat room is open, false otherwise
     */
    public boolean isRoomOpen(int chatRoomID) {
        return rooms.get(chatRoomID).getIsOpen();
    }
    /**
     * Returns the number of users currently in the chat server.
     *
     * @return the number of users
     */
    public int getNumberOfUsers() {
        return users.size();
    }
    /**
     * Returns the main chat room of the server.
     *
     * @return the main chat room
     */
    public ChatRoom getMain() {
        return rooms.get(0);
    }
    /**
     * Returns the list of all chat rooms in the server.
     *
     * @return the list of chat rooms
     */
    public ArrayList<ChatRoom> getRoomsList() {
        return rooms;
    }
}