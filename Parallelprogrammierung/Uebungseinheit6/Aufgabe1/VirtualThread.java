package Uebungseinheit6.Aufgabe1;

public class VirtualThread {
    public void run() {
        Runnable outputTest = () -> {
            output("Hi");
            output("Working");
            output("Bye");
        };

        var t1 = Thread.ofVirtual().name("Thread A").start(outputTest);
        var t2 = Thread.ofVirtual().name("Thread B").start(outputTest);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void output(String text) {
        System.out.println(Thread.currentThread().threadId() + " - " + Thread.currentThread().getName() + ": " + text);
        sleep(1000);

    }

    void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
