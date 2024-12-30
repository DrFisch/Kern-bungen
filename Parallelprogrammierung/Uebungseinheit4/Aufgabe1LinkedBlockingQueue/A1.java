package Aufgabe1LinkedBlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;

public class A1 {
    public static void main(String[] args) {
        var q = new LinkedBlockingQueue<String>();
        var p = new Producer(10, q); 
        var c1 = new Consumer(q); 
        var c2 = new Consumer(q);
        
        double start = System.nanoTime(); 

        var t1 = new Thread(p); 
        t1.start();

        var t2 = new Thread(c1); 
        t2.start(); 

        var t3 = new Thread(c2); 
        t3.start();

        try { 
            t1.join(); 
            t2.join(); 
            t3.join(); 
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
        
        double end = System.nanoTime();
        System.out.print(String.format("%.2f ms", (end - start) / 1000 / 1000));
    }
}

class Producer implements Runnable {
    private final LinkedBlockingQueue<String> q;
    private final int count;
    private static final String TERMINATION_SIGNAL = "STOP";

    public Producer(int count, LinkedBlockingQueue<String> q) {
        this.q = q;
        this.count = count;
    }

    public void run() {
        Random random = new Random();
        try {
            for (int i = 0; i < count; i++) {
                String number = Integer.toString(random.nextInt(100));
                System.out.println("Producer erzeugte: " + number);
                q.put(number);
            }
            
            q.put(TERMINATION_SIGNAL);
            q.put(TERMINATION_SIGNAL);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

class Consumer implements Runnable {
    private final LinkedBlockingQueue<String> q;
    private static final String TERMINATION_SIGNAL = "STOP";

    public Consumer(LinkedBlockingQueue<String> q) {
        this.q = q;
    }

    public void run() {
        try {
            while (true) {
                String item = q.take();
                if (TERMINATION_SIGNAL.equals(item)) {
                    System.out.println("Konsumieren beendet");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " hat gegessen: " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Consumer interrupted: " + e.getMessage());
        }
    }
}
