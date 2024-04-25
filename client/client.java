import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class client {

	static void printBoard(String[] spaces) 
	{
		System.out.println("|---|---|---|");
        System.out.println("| " + spaces[0] + " | " + spaces[1] + " | " + spaces[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + spaces[3] + " | " + spaces[4] + " | " + spaces[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + spaces[6] + " | " + spaces[7] + " | " + spaces[8] + " |");
        System.out.println("|---|---|---|");
	}

	static String checkResults(String[] spaces)
    {
        boolean isTie = true;
        for (String s : spaces) 
        {
            if (s != "X" && s != "O")
                isTie = false;
        }
        if (isTie)
            return "Tie";

        String result = "";
        for (int i = 0; i < 8; i++) 
        {
            switch (i) 
            {
                case 0:
                    result = spaces[0] + spaces[1] + spaces[2];
                    break;
                case 1:
                    result = spaces[3] + spaces[4] + spaces[5];
                    break;
                case 2:
                    result = spaces[6] + spaces[7] + spaces[8];
                    break;
                case 3:
                    result = spaces[0] + spaces[3] + spaces[6];
                    break;
                case 4:
                    result = spaces[1] + spaces[4] + spaces[7];
                    break;
                case 5:
                    result = spaces[2] + spaces[5] + spaces[8];
                    break;
                case 6:
                    result = spaces[0] + spaces[4] + spaces[8];
                    break;
                case 7:
                    result = spaces[2] + spaces[4] + spaces[6];
                    break;
            }
            if (result.equals("XXX")) 
                return "Client";
            else if (result.equals("OOO"))
                return "Server";
            else 
                return "Continue";
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        String command;
        String modifiedcommand;
		String introMessage;
		String[] spaces = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String result = "";
        int response = 0;
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
		introMessage = inFromServer.readLine();
		System.out.println(introMessage);
		printBoard(spaces);
		System.out.println("Enter a number displayed on the board:");
		while(true) {
			command = inFromUser.readLine();
            // System.out.println("COMMAND: " + command);

			// Send line to server
			try 
			{
				response = Integer.parseInt(command);
			}
			catch (NumberFormatException e) 
			{
				command = "\"" + command + "\"" + " is an invalid input, try again\n";
				outToServer.writeBytes(command);
				continue;
			}
			spaces[response - 1] = "X";
			outToServer.writeBytes(command + "\n");
            // System.out.println("SENT TO SERVER: " + command);

			// Read line from server
            TimeUnit.SECONDS.sleep(5);
			modifiedcommand = inFromServer.readLine();
            // System.out.println("RECEIVED FROM SERVER: " + modifiedcommand);
			// modifiedcommand = "2";

            response = Integer.parseInt(modifiedcommand);
			//System.out.println(modififedcommand);
            
			if("It's a tie!".equals(modifiedcommand)) {
				System.out.println("It's a tie!");
				clientSocket.close();
				break;
			}

			// This if needs to be fixed
			/*else if("joke1.txt".equals(modififedcommand)) {
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
			}*/
			else {
                    spaces[response - 1] = "O";
                    printBoard(spaces);
                    result = checkResults(spaces);
                    if (result.equals("Server") || result.equals("Client") || result.equals("Tie"))
                        break;
                    System.out.println("-------------");
                    printBoard(spaces);
                    System.out.println("Enter a number displayed on the board:");
                    try 
                    {
                        response = Integer.parseInt(modifiedcommand);
                    }
                    catch (NumberFormatException e) 
                    {
                        modifiedcommand = "\"" + modifiedcommand + "\"" + " is an invalid input, try again\n";
                        outToServer.writeBytes(modifiedcommand);
                        continue;
                    }
                    // outToServer.writeBytes(modifiedcommand);
			}

		}
    }
}

//Hello! Let's play tic-tac-toe!