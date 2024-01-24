/**

 A class that represents a chat room, with the ability to open and close the room, enter and leave the room,

 and check the status of the room. The room has a capacity for a certain number of users to join and interact with each other.
 */
import java.util.ArrayList;
import java.util.List;

//passive test
// Is this a passive or active class?
public class ChatRoom {
	
	private int chatRoomID;
	private int capacity;
	private List<User> users;
	private boolean isOpen;
	/**

	 Creates a new ChatRoom instance with the given ID number and capacity. Initially, the chat room is closed.
	 @param chatRoomID The ID number of the chat room.
	 @param capacity The maximum number of users that can be in the chat room.
	 */
	public ChatRoom(int chatRoomID, int capacity) {
		this.chatRoomID = chatRoomID;
		this.capacity = capacity;
		this.isOpen= false;
		this.users= new ArrayList<User>();
	}
	// Consider if this should be run asynchronously.
	/**

	 Opens the chat room. If the chat room is already open, nothing happens.
	 */
	public void open() {
		// Code to open the Chat Room.
		if (!isOpen){
			isOpen=true;
			System.out.println("Chat Room " + chatRoomID + " open!");
		}

	}
	/**

	 Closes the chat room. If the chat room is already closed or there are still users in the room, nothing happens.
	 */
	// Consider if this should be run asynchronously.
	public void close() {
		System.out.println("Chat Room " + chatRoomID + " is being closed!");
		if(isOpen && users.isEmpty()){
			isOpen=false;
			System.out.println("Chat Room " + chatRoomID + " closed!");
		}
	}
	/**

	 Adds a user to the chat room. The user can only join if the room is open and has not reached its capacity.
	 @param user The User instance to be added to the chat room.
	 @return true if the user is successfully added to the chat room, false otherwise.
	 */
	public synchronized boolean enterRoom(User user) {
		if(isOpen && users.size() < capacity){
			users.add(user);
			System.out.println("User " + user.getID() + " joined Chat Room. " + chatRoomID + ". (" + user.getWantToChat() + ")" + this);
			return true;

		}else
			System.out.println("User " + user.getID() + " not joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");

		return false;
		//System.out.println("User " + user.getID() + " joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
	}
	/**

	 Removes a user from the chat room.
	 @param user The User instance to be removed from the chat room.
	 */
	// Consider if this should be run asynchronously.
	public void leaveRoom(User user) {
		// Code for a User to leave a Chat Room.
		users.remove(user);
		System.out.println("User " + user.getID() + " left Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
		if(!isOpen && users.size() ==0){
			//close();
		}
	}
	/**

	 Sets the status of the chat room: open or closed.
	 @param isOpen true if the chat room is open, false if it is closed.
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	/**

	 Gets the status of the chat room: open or closed.

	 @return true if the chat room is open, false if it is closed.
	 */
		public boolean getIsOpen() {
		return isOpen;
	}
	/**

	 Sets the status of the chat room: open or closed.
	 @param isOpen true if the chat room is open, false if it is closed.
	 */
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
