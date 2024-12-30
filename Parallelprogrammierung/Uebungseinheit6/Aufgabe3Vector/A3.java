package Uebungseinheit6.Aufgabe3Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class A3 {
    public static void main(String[] args) {
        
        var vector1 = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        var vector2 = new Double[]{4.0, 5.0, 6.0, 4.0, 5.0, 6.0};

        runParallel(vector1, vector2, Double::sum,(el1, el2) -> el1 * el2,(el1, el2) -> -(el1 * el2));
    }
    
    @SafeVarargs
    public static void runParallel(Double[] vector1, Double[] vector2, 
                                   BiFunction<Double, Double, Double> aggregateFunc, 
                                   BiFunction<Double, Double, Double>... elementOperations) {
        // Results list (thread-safe)
        var results = Collections.synchronizedList(new ArrayList<Double>());

        // CyclicBarrier, das nach Abschluss aller Operationen aggregiert
        var barrier = new CyclicBarrier(
            vector1.length * elementOperations.length,
            new AggregateOperation<>(aggregateFunc, results)
        );
        List<Thread> threads=new ArrayList<>();
        // Starte alle Operationen parallel
        for (var operation : elementOperations) {
            IntStream.range(0, vector1.length).forEach(i -> {
                
                threads.add(Thread.ofVirtual().start(new ElementOperation<>(operation, vector1[i], vector2[i], results, barrier)));
            }
            );
        }
        threads.stream().parallel().forEach(thread -> { 
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        });
    }
    record ElementOperation<T1, T2, R>(BiFunction<T1, T2, R> operation, T1 element1, T2 element2, 
                                       List<R> results, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            results.add(operation.apply(element1, element2));
            try {
                barrier.await(); // Warten, bis alle Threads fertig sind
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    record AggregateOperation<R>(BiFunction<R, R, R> func, List<R> results) implements Runnable {
        @Override
        public void run() {
            if (results.isEmpty()) {
                throw new IllegalArgumentException("Results list is empty!");
            }

            // Reduziere die Ergebnisse
            R aggregatedResult = results.stream()
                                         .reduce((x,y)->func.apply(x, y))
                                         .orElseThrow(() -> new IllegalArgumentException("Reduction failed!"));

            System.out.println("Aggregated Result: " + aggregatedResult);
        }
    }
}
