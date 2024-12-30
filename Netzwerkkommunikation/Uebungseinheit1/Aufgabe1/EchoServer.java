package Uebungseinheit1.Aufgabe1;

//PS C:\Users\lukas\OneDrive\Verwaltungsinformatik\5. Semester\Fortgeschrittene Programmiertechniken\Programme\Netzwerkkommunikation\Uebungseinheit1\Aufgabe1> java Uebungseinheit1.Aufgabe1.EchoServer 5000

import java.net.UnknownHostException;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;;

/**
 * Der EchoServer empfängt Nachrichten vom Client und schickt sie zurück.
 */
public class EchoServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        System.out.println("EchoServer startet auf Port " + portNumber + "...");

        try (
            // Server-Socket erstellen und auf Verbindungen warten
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept(); // Client-Verbindung akzeptieren
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            System.out.println("Client verbunden: " + clientSocket.getInetAddress());

            String inputLine;

            // Eingehende Nachrichten vom Client lesen und zurücksenden
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Empfangen: " + inputLine);
                out.println(inputLine); // Echo zurück an den Client senden
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Hören auf Port " + portNumber);
            System.out.println(e.getMessage());
        }
    }
}