Joshua Delgado 7622-0516 joshua.delgado@ufl.edu

Nathan Jones 1313-5843 njones2@ufl.edu

Shane Cross 2135-8090 shanecross@ufl.edu

Steps to Run the Program

1. To begin compiling and running our code for this assignment, two systems need to be set up with
the files in our assignment submission, with one system representing the “server” and the other
system representing the “player”. First, you must download the respective files on each system.
The server having the server.java and the player having client.java.

2. Now that you have the files on their respective systems they need to be compiled. To compile them
you must navigate to the project directory using the cd command. Once the system is in the correct spot
they can compile using the following java command: Player - javac client.java // Server - javac server.java

3. When running the server and client make sure the server is ran first. The setup for the command on the client side is java client [connection method(localhost, IP address, etc.)] [port number(516)].
The setup for the command on the server side is always java server [port number(516)].
If running the program through localhost- they can be ran using the following java commands: Player - java client localhost 516 // Server - java server 516.
