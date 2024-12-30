package Uebungseinheit1.Aufgabe2;

//PS C:\Users\lukas\OneDrive\Verwaltungsinformatik\5. Semester\Fortgeschrittene Programmiertechniken\Programme\Netzwerkkommunikation> java Uebungseinheit1.Aufgabe1.EchoClient localhost 5000

import java.net.UnknownHostException;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;;

/**
 * Der EchoClient stellt eine Verbindung zum Server her,
 * sendet Benutzereingaben und gibt die Serverantwort aus.
 */
public class EchoClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            // Verbindung zum Server herstellen
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;

            System.out.println("Verbindung hergestellt! Geben Sie eine Nachricht ein:");

            // Benutzereingaben an den Server senden und die Antwort ausgeben
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput); // Nachricht an den Server senden
                System.out.println("echo: " + in.readLine()); // Antwort vom Server
            }

        } catch (UnknownHostException e) {
            System.err.println("Unbekannter Host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Keine I/O-Verbindung zu " + hostName);
            System.exit(1);
        }
    }
}
