package Uebungseinheit6.Aufgabe2;

import java.util.ArrayList;
import java.util.List;

public class VirtualThread2 {
    public void run() {
        Runnable outputTest = () -> {
            output("Hi");
            output("Working");
            output("Bye");
        };

        List<Thread> threads = new ArrayList<>();
        for (int i=0; i<10; i++)
            threads.add(Thread.ofVirtual().name("Virtual Thread " + i).start(outputTest));

        try {
            threads.stream().parallel().forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void output(String text) {
        // Output zeigt erst Virtual Thread und dann jeweiligen Carrier Thread als Worker sowie Ausgabetext
        // z.B. VirtualThread[#1012]/runnable@ForkJoinPool-1-worker-2: Bye
        System.out.println(Thread.currentThread() + ": " + text);
        sleep(100);
    }

    void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
