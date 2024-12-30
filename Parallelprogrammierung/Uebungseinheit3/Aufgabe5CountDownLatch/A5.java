package Uebungseinheit3.Aufgabe5CountDownLatch;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Function;

public class A5 {
    public static void main(String[] args) {
        new A5().run();
    }
    public void run(){
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        runParallel(vector, results -> System.out.println(Arrays.stream(results).reduce(0., (x,y)->x+y)),
            el -> el * el,
            el -> el + 4
        );
    }
    @SafeVarargs
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        for (int operationIndex = 0; operationIndex < elementOperations.length; operationIndex++) {
            // CountDownLatch für die Anzahl der Elemente initialisieren
            CountDownLatch cdl = new CountDownLatch(vector.length);

            for (int i = 0; i < vector.length; i++) {
                // Führe die aktuelle Operation auf jedes Element parallel aus
                new Thread(new ElementOperation<>(elementOperations[operationIndex], vector, i, cdl)).start();
            }

            // Warten, bis alle Threads fertig sind
            try {
                cdl.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        new ResultOperation<>(vector, resultOperation).run();
    }
    
    record ElementOperation<T>(Function<T, T> operation, T[] vector, int index, CountDownLatch latch) implements Runnable {
        @Override
        public void run() {
            // Operation auf das Element anwenden
            vector[index] = operation.apply(vector[index]);

            // Zähler des Latch reduzieren
            latch.countDown();
        }
    }
    record ResultOperation<R>(R[] results, Consumer<R[]> operation) implements Runnable {
        @Override
        public void run() {
            if (results.length == 0)
                throw new IllegalArgumentException("Array size must be greater than 0!");

            // Finale Operation auf das Ergebnisarray anwenden
            operation.accept(results);
        }
    }
    

    // record ElementOperation<T>(Function<T, T> operation, T[] vector, int index, CountDownLatch cdl) implements Runnable {
    //     @Override
    //     public void run() {
    //         vector[index] = operation.apply(vector[index]);

    //         try { cdl.await(); }
    //         catch (Exception e) { e.printStackTrace(); }
    //     }
    // }
    

    // record ResultOperation<R>(R[] results, Consumer<R[]> operation) implements Runnable {
    //     @Override
    //     public void run() {
    //         if (results.length == 0)
    //             throw new IllegalArgumentException("Array size must be greater than 0!");

    //         operation.accept(results);
    //     }
    // }
}
