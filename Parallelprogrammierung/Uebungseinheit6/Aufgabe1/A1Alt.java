package Uebungseinheit6.Aufgabe1;

public class A1Alt implements Runnable {

    private String threadName;

    // Konstruktor, um den Thread-Namen zu setzen
    public A1Alt(String threadName) {
        this.threadName = threadName;
    }

    // Implementierung der run()-Methode
    @Override
    public void run() {
        out("Hi");
        out("Working");
        out("Bye");
    }

    static void out(String text) {
        System.out.println(Thread.currentThread().threadId() + " - "
                + Thread.currentThread().getName() + ": " + text);
        sleep(1000);
    }

    static void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Erstelle zwei Instanzen der Klasse A1
        var runnable1 = new A1Alt("Thread A");
        var runnable2 = new A1Alt("Thread B");

        // Starte Virtual Threads mit den Runnable-Instanzen
        var t1 = Thread.ofVirtual().name(runnable1.threadName).start(runnable1);
        var t2 = Thread.ofVirtual().name(runnable2.threadName).start(runnable2);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
