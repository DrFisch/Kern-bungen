package Uebungseinheit1.Aufgabe1;

import java.net.UnknownHostException;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;;

public class A1 {
    
}


class EchoClient {
    public static void main(String[] args) throws IOException {
    if (args.length != 2) { System.err.println("Usage: java EchoClient "
    + "<host name> <port number>"); System.exit(1);
    }
    var hostName = args[0]; var portNumber = Integer.parseInt(args[1]);
    try (var echoSocket = new Socket(hostName, portNumber);
    var out = new PrintWriter(echoSocket.getOutputStream(), true);
    var in = new BufferedReader(new InputStreamReader(
    echoSocket.getInputStream()));
    var stdIn=new BufferedReader(new InputStreamReader(System.in))
    ) {
        String userInput;
while ((userInput = stdIn.readLine()) != null) {
out.println(userInput);
System.out.println("echo: " + in.readLine());
}
} catch (UnknownHostException e) {
System.err.println("Don't know about host " + hostName);
System.exit(1);
} catch (IOException e) {
System.err.println("Couldn't get I/O for the connection to "
+ hostName);
System.exit(1);
}
}
}

class EchoServer {
    public static void main(String[] args) throws IOException {
    if (args.length != 1) { System.err.println("Usage: java EchoServer"
    + "<port number>"); System.exit(1); }
    int portNumber = Integer.parseInt(args[0]);
    try (var serverSocket = new ServerSocket(Integer.parseInt(args[0]));
    var clientSocket = serverSocket.accept();
    var out = new PrintWriter(clientSocket.getOutputStream(),true);
    var in = new BufferedReader(new InputStreamReader(
    clientSocket.getInputStream()));
    ) {
        String inputLine;
while ((inputLine = in.readLine()) != null) {
out.println(inputLine);
}
} catch (IOException e) {
System.out.println(
"Exception caught when trying to listen on port "
+ portNumber + " or listening for a connection");
System.out.println(e.getMessage());
}
}
}