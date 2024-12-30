package Aufgabe2ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class A2 {
    public static void main(String[] args) {
        new ProducerConsumerQueueCLQ().run();
    }
}



class ProducerConsumerQueueCLQ {
    public void run() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        Producer p = new Producer(1000, q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);

        double start = System.nanoTime();

        var t1 = new Thread(p); t1.start();
        var t2 = new Thread(c1); t2.start();
        var t3 = new Thread(c2); t3.start();

        try {
            t1.join(); t2.join(); t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double end = System.nanoTime();
        System.out.print(String.format("%.2f ms", (end - start) / 1000 / 1000));
    }

    static final String END_TOKEN = "BYE";

    record Producer(int max, ConcurrentLinkedQueue<String> queue) implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < max; i++) {
                    queue.add(produce());
                }

                
                queue.add(END_TOKEN);
                queue.add(END_TOKEN);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Producer " + Thread.currentThread().threadId() + " finished");
        }

        String produce() {
            final var result = ThreadLocalRandom.current().nextInt(100);

            //System.out.println("Producer " + Thread.currentThread().threadId() + " calculated " + result);

            return String.valueOf(result);
        }
    }

    record Consumer(ConcurrentLinkedQueue<String> queue) implements Runnable {
        public void run() {
            try {
                while (true) {
                    String x = queue.poll(); // Entfernt das Element oder gibt null zurück

                    if (x == null) {
                        
                        Thread.sleep(1);
                        continue;
                    }

                    if (END_TOKEN.equals(x)) {
                        break;
                    }

                    consume(x);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println("Consumer " + Thread.currentThread().threadId() + " finished");
        }

        void consume(Object x) {
            //System.out.println("Consumer " + Thread.currentThread().threadId() + " consumed " + x);
        }
    }

    
}
