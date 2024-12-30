package Uebungseinheit1.Aufgabe2;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class MultiEchoServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java MultiEchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        System.out.println("MultiEchoServer gestartet. Lauscht auf Port " + portNumber);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                // Auf eine Client-Verbindung warten und einen neuen Thread starten
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neuer Client verbunden: " + clientSocket.getInetAddress());

                new EchoServerThread(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lauschen auf Port " + portNumber);
            e.printStackTrace();
        }

        System.out.println("MultiEchoServer beendet.");
    }
    
}
class EchoServerThread extends Thread {
    private Socket socket = null;

    public EchoServerThread(Socket socket) {
        super("EchoServerThread");
        this.socket = socket;
    }
    public void run() {
        try (var out = new PrintWriter(socket.getOutputStream(), true);
        var in = new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
        ) {
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
        out.println(inputLine);
        if (inputLine.equals("Bye"))
        break;
        }
        socket.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}