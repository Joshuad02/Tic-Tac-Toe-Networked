import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class server {

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
        String modifiedSentence;
        String[] spaces = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String result = "";
        int response = 0;

        // Establish port
        int port = Integer.parseInt(args[0]);

        // Create socket at port
        ServerSocket servSocket = new ServerSocket(port);        
        
        OutputStream fileOut = null;
        while(true) {
            Socket connectionSocket = servSocket.accept();
            String clientIP = connectionSocket.getRemoteSocketAddress().toString();
            System.out.println("Client accepted with IP: " + clientIP.substring(1));

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            // Create output stream attached to the socket
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            outToClient.writeBytes("Let's play tic-tac-toe! You are playing with X's.\n");
            while((command = inFromClient.readLine()) != null) {
                // System.out.println("RECEIVED FROM CLIENT1: " + command);
                if("bye".equals(command)) {
                    outToClient.writeBytes("Disconnected\n");
                    System.out.println("Disconnected");
                    //TimeUnit.SECONDS.sleep(1);
                    break;
                }
                else {
                    // Desired modifications to sentence
                    try 
                    {
                        response = Integer.parseInt(command);
                    }
                    catch (NumberFormatException e) 
                    {
                        modifiedSentence = "\"" + command + "\"" + " is an invalid input, try again\n";
                        outToClient.writeBytes(modifiedSentence);
                        continue;
                    }
                    spaces[response - 1] = "X";
                    printBoard(spaces);
                    result = checkResults(spaces);
                    if (result.equals("Server") || result.equals("Client") || result.equals("Tie"))
                        break;
                    System.out.println("Enter a number displayed on the board:");
                    modifiedSentence = inFromUser.readLine();
                    // System.out.println("MODIFIED: " + modifiedSentence);
                    outToClient.writeBytes(modifiedSentence + '\n');
                    // System.out.println("SENT TO CLIENT MODIFIED: " + modifiedSentence);

                    
                    try 
                    {
                        response = Integer.parseInt(modifiedSentence);
                    }
                    catch (NumberFormatException e) 
                    {
                        modifiedSentence = "\"" + modifiedSentence + "\"" + " is an invalid input, try again\n";
                        outToClient.writeBytes(modifiedSentence);
                        continue;
                    }
                    spaces[response - 1] = "O";
                    printBoard(spaces);
                    System.out.println("-------------");
                    
                }

            }
            if (result.equals("Client")) 
            {
                outToClient.writeBytes("You win!");
                System.out.println("You lose...");
            }
            else if (result.equals("Server")) 
            {
                outToClient.writeBytes("You lose...");
                System.out.println("You win!!");
            }
            else if (result.equals("Tie")) 
            {
                outToClient.writeBytes("It's a tie!");
                System.out.println("It's a tie!");
            }

            outToClient.close();
            inFromClient.close();

        }

    }
};
