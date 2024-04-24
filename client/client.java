import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws Exception {
        String command;
        String modififedcommand;
		String hello;
		// Establish port
		int port = Integer.parseInt(args[1]);

		// Create client socket and connect to server
		// On local machine: "192.168.1.106"
		Socket clientSocket = new Socket(args[0], port);
        
		// Create input stream
			
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			
		// Create output stream that is attached to the socket
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		// Create input stream attached to socket
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		System.out.println("Connected");
		hello = inFromServer.readLine();
		System.out.println(hello);
		while(true) {
			command = inFromUser.readLine();

			// Send line to server
			outToServer.writeBytes(command + "\n");
			System.out.println("Sent to server: " + command);
			// Read line from server
			modififedcommand = inFromServer.readLine();

			//System.out.println(modififedcommand);
			if("Disconnected".equals(modififedcommand)) {
				System.out.println("exit");
				clientSocket.close();
				break;
			}
			// This if needs to be fixed
			else if("joke1.txt".equals(modififedcommand)) {
				System.out.println("Receiving joke 1");
				// New part: Receive the file size from the server
				DataInputStream dataInFromServer = new DataInputStream(clientSocket.getInputStream());
				long fileSize = dataInFromServer.readLong(); // Read the file size first
				long bytesReceived = 0;
				
				// Prepare to receive the file
				FileOutputStream fileOut = new FileOutputStream("joke1.txt");
				byte[] buffer = new byte[8192];
				
				while(bytesReceived < fileSize) {
					int count = dataInFromServer.read(buffer);
					if (count > 0) {
						fileOut.write(buffer, 0, count);
						bytesReceived += count;
					} else {
						// End of file data stream
						break;
					}
				}
				System.out.println("Received from server: What kind of shoes does a spy wear? Sneakers");
				System.out.println("Finished receiving file");
				fileOut.close();
			}
			else if("joke2.txt".equals(modififedcommand)) {
				System.out.println("Receiving joke 2");
				// New part: Receive the file size from the server
				DataInputStream dataInFromServer = new DataInputStream(clientSocket.getInputStream());
				long fileSize = dataInFromServer.readLong(); // Read the file size first
				long bytesReceived = 0;

				// Prepare to receive the file
				FileOutputStream fileOut = new FileOutputStream("joke2.txt");
				byte[] buffer = new byte[8192];

				while(bytesReceived < fileSize) {
					int count = dataInFromServer.read(buffer);
					if (count > 0) {
						fileOut.write(buffer, 0, count);
						bytesReceived += count;
					} else {
						// End of file data stream
						break;
					}
				}
				System.out.println("Received from server: What does a house wear? Address");

				System.out.println("Finished receiving file");
				fileOut.close();
			}
			else if("joke3.txt".equals(modififedcommand)) {
				System.out.println("Receiving joke 3");
				// New part: Receive the file size from the server
				DataInputStream dataInFromServer = new DataInputStream(clientSocket.getInputStream());
				long fileSize = dataInFromServer.readLong(); // Read the file size first
				long bytesReceived = 0;

				// Prepare to receive the file
				FileOutputStream fileOut = new FileOutputStream("joke3.txt");
				byte[] buffer = new byte[8192];

				while(bytesReceived < fileSize) {
					int count = dataInFromServer.read(buffer);
					if (count > 0) {
						fileOut.write(buffer, 0, count);
						bytesReceived += count;
					} else {
						// End of file data stream
						break;
					}
				}
				System.out.println("Received from server: Why don't blind people skydive? Because it scares their dogs");
				System.out.println("Finished receiving file");
				fileOut.close();
			}
			else {
				System.out.println(modififedcommand);
			}

		}



    }
}