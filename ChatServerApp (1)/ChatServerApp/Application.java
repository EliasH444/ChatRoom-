/**

 The Application class contains the main method to start the chat application.
 It creates an instance of the Admin class and a ChatServer object with the given capacity and number of chat rooms.
 It then creates 20 user threads with random user IDs and starts them.
 After starting the user threads, the main thread waits for all user threads to complete before terminating.
 @author [Elias Hassan ]
 @version 1.0
 */
import java.util.ArrayList;
public class Application   {
	
	public static void main(String[] args) throws InterruptedException {



		Admin admin = new Admin("Liam");
		Thread at = new Thread(admin);
		ChatServer cs = new ChatServer(10, 2, admin);
		ChatRoom roosm = new ChatRoom(1,2);
		//roosm.open();
		roosm.close();

		//open server initially then chatroom.in run method is close b

		ArrayList<Thread> userThreads = new ArrayList<Thread>();

		at.start();
		//thread for admin.thread for users.

		// Create 20 users with random UserIDs (1-100) and start their threads
		for (int i = 0; i < 20; i++) {
			int userID = (int)(Math.random() * (100-1+1) + 1);
			User u1 = new User(userID, cs);
			Thread u1t = new Thread(u1);
			u1t.start();
		//	room.close();
		}

		// Make sure this waits for all user threads to end
		for (Thread t : userThreads) {
			t.join();
		}

		at.join();
	}
}