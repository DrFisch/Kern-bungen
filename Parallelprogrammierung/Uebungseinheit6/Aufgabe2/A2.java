package Uebungseinheit6.Aufgabe2;

import java.util.ArrayList;
import java.util.List;

public class A2 {
    public static void main(String[] args) {
        Runnable outputTest = () -> {
            out("Hi"); 
            out("Working"); 
            out("Bye");
        };

        
        List<Thread> threads = new ArrayList<>();
        for (int i=0; i<10; i++){
            threads.add(Thread.ofVirtual().name("Thread "+i).start(outputTest));
        }
        
        threads.stream().parallel().forEach(thread -> { 
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        });
        
    }
    
    static void out(String text) {
        System.out.println(Thread.currentThread().threadId() + " - "
        + Thread.currentThread().getName() + ": " + text); sleep(1000);
    }
    static void sleep(long t) { 
        try { Thread.sleep(t); 
        } 
        catch (Exception e){} 
    }
}
