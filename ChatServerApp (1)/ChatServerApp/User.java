import java.util.ArrayList;
import java.util.List;


/**

 This class represents a user in the chat application. Users are runnable objects that can join the chat server,
 join chat rooms and send messages. The user class is responsible for keeping track of its own state, including
 which chat rooms it is currently in and how many messages it wants to send.
 */
public class User implements Runnable{

	private static int sleepScale = 1000;
	private int userID;
	private ChatServer chatServer;//shared
	ChatRoom chatRoom;
	//private ChatRoom chatRoom;

	private boolean joinedServer;
	private boolean joinedMainRoom;
	private ArrayList<ChatRoom> rooms;
	private int wantToChat;
	/**
	 * Constructs a new User object with the given user ID and chat server.
	 *
	 * @param userID the ID of the user
	 * @param chatServer the chat server to join
	 */
	public User(int userID, ChatServer chatServer) {
		// Int Range (MAX, MIN) -> (int)Math.random() * (MAX-MIN+1) + MIN
		this.userID = userID;
		this.chatServer = chatServer;
		//this.wantToChat = (int) (Math.random() * (15 - 10 + 1) + 10);
		this.wantToChat = (int)(Math.random() * (15-10+1) + 10);
		this.joinedServer = false;
		this.joinedMainRoom=false;
		//chatServer.users.add(this);
	}
	/**
	 * Returns the number of messages the user wants to send.
	 *
	 * @return the number of messages the user wants to send
	 */
	public int getWantToChat() {
		return wantToChat;
	}
	/**
	 * Returns the ID of the user.
	 *
	 * @return the ID of the user
	 */
	public int getID() {
		return userID;
	}

	/**
	 * This method is called when the user's thread is started. The user will join the chat server and
	 * attempt to join chat rooms until it no longer wants to chat.
	 */
	@Override

	public void run() {
		try {
			// While the user is still intersted in chatting ...
			while (wantToChat > 0) {
				if (!joinedServer) {
					chatServer.join(this);
					wantToChat = (int) (wantToChat - 0.5);
					Thread.sleep((long)Math.random() * (2*sleepScale - sleepScale)+ sleepScale);
					joinedServer=true;
				} else if (!joinedMainRoom) {
					// Try and join Main Chat Room
					int chatRoomID = (int) Math.random() * 10;
					chatServer.enterRoom(this,0);
					ChatRoom firstRoom = chatServer.getMain();
					firstRoom.enterRoom(this);
					wantToChat = wantToChat - 1;
					Thread.sleep((long)Math.random() * (2*sleepScale - sleepScale)+ sleepScale);
					joinedMainRoom=true;
					chatServer.leaveRoom(this,chatRoomID);
				} else {
					// Try and join a random Chat Room
					int randomRoomIndex = (int) (Math.random() * chatServer.getRoomsList().size());
					ChatRoom randomRoom = chatServer.getRoomsList().get(randomRoomIndex);
					randomRoom.enterRoom(this);
					wantToChat = wantToChat - 2;
					Thread.sleep((long)Math.random() * (2*sleepScale - sleepScale)+ sleepScale);
				}
			}
			// What should happen when the user no longer wants to chat?
			chatServer.leave(this);
		} catch (InterruptedException ex) {
			System.out.println("Interrupted User Thread (" + userID + ")");
		}
		System.out.println("User Thread (" + userID + ") has ended!");
	}
}