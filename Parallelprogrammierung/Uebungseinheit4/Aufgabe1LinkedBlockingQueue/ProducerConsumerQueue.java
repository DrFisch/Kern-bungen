package Aufgabe1LinkedBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

class ProducerConsumerQueue {
    public void run() {
        BlockingQueue<String> q = new LinkedBlockingQueue<>();
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
        System.out.print(String.format("%.2f ms", (end-start) / 1000 / 1000));
    }

    static final String END_TOKEN = "BYE";

    record Producer(int max, BlockingQueue<String> queue) implements Runnable {
           public void run() {
            try {
                for (int i=0; i<max; i++) {
                    queue.put(produce());
                }

                // Ansatz, wenn Anzahl Consumer bekannt (alternativ muss Consumer END_TOKEN nach Lesen wieder Reinsetzen)
                queue.put(END_TOKEN); queue.put(END_TOKEN);
            } catch (InterruptedException ex) { ex.printStackTrace(); }

            System.out.println("Producer " + Thread.currentThread().threadId() + " finished");
        }

        String produce() {
            final var result = ThreadLocalRandom.current().nextInt(100);

            //System.out.println("Producer " + Thread.currentThread().threadId() + " calculated " + result);

            return String.valueOf(result);
        }
    }

    record Consumer(BlockingQueue<String> queue) implements Runnable {
        public void run() {
            try {
                while (!END_TOKEN.equals(queue.peek())) {
                    final var x = queue.take();

                    // Da Thread unterbrochen werden kann zwischen while peek() und take()
                    // kann ggf. END_TOKEN durchrutschen, weshalb Zusatzabfrage hier nötig ist
                    if (END_TOKEN.equals(x))
                        break;

                    consume(x);
                }
            } catch (InterruptedException ex) { ex.printStackTrace(); }

            System.out.println("Consumer " + Thread.currentThread().threadId() + " finished");
        }

        void consume(Object x) {
            //System.out.println("Consumer " + Thread.currentThread().threadId() + " consumed " + x);
        }
    }

}