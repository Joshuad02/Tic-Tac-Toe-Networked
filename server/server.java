import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class server {
    public static void main(String[] args) throws Exception {
        String command;
        String modififedSentence;

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
            outToClient.writeBytes("Hello!\n");
            while((command = inFromClient.readLine()) != null) {
                System.out.println("COMMAND: " + command);
                if("bye".equals(command)) {
                    outToClient.writeBytes("Disconnected\n");
                    System.out.println("Disconnected");
                    //TimeUnit.SECONDS.sleep(1);
                    break;
                }
                else if("Joke 1".equals(command)) {
                    System.out.println("Client requested: " + command + ", returning joke");
                    outToClient.writeBytes("joke1.txt\n");
                    TimeUnit.SECONDS.sleep(1);
    
                    File file = new File("../jokes/joke1.txt");
                    long length = file.length();
                    outToClient.writeLong(length);
                    byte[] bytes = new byte[8192];
                    InputStream in = new FileInputStream(file);
                    OutputStream out = connectionSocket.getOutputStream();
                    
                    int count;
                    while((count = in.read(bytes)) > 0) {
                        out.write(bytes, 0, count);
                    }

                    in.close();

                }
                else if("Joke 2".equals(command)) {
                    System.out.println("Client requested: " + command + ", returning joke");
                    outToClient.writeBytes("joke2.txt\n");
                    TimeUnit.SECONDS.sleep(1);
    
                    File file = new File("../jokes/joke2.txt");
                    long length = file.length();
                    outToClient.writeLong(length);
                    byte[] bytes = new byte[8192];
                    InputStream in = new FileInputStream(file);
                    OutputStream out = connectionSocket.getOutputStream();
                    
                    int count;
                    while((count = in.read(bytes)) > 0) {
                        out.write(bytes, 0, count);
                    }
                    in.close();

                }
                else if("Joke 3".equals(command)) {
                    System.out.println("Client requested: " + command + ", returning joke");
                    outToClient.writeBytes("joke3.txt\n");
                    TimeUnit.SECONDS.sleep(1);
    
                    File file = new File("../jokes/joke3.txt");
                    long length = file.length();
                    outToClient.writeLong(length);
                    byte[] bytes = new byte[8192];
                    InputStream in = new FileInputStream(file);
                    OutputStream out = connectionSocket.getOutputStream();
                    
                    int count;
                    while((count = in.read(bytes)) > 0) {
                        out.write(bytes, 0, count);
                    }
                    in.close();

                }
                else {
                    // Desired modifications to sentence
                    modififedSentence = "\"" + command + "\"" + " is an invalid input, try again\n";
                    outToClient.writeBytes(modififedSentence);
                }

            }
            outToClient.close();
            inFromClient.close();

        }

    }
};