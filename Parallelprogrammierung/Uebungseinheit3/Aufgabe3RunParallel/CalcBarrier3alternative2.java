package Uebungseinheit3.Aufgabe3RunParallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class CalcBarrier3alternative2 {

    private static CyclicBarrier barrier;

    @SuppressWarnings("unchecked")
    public void run() {
        final Double[] vector1 = {1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        final Double[] vector2 = {4.0, 5.0, 6.0, 4.0, 5.0, 6.0};

        runParallel(vector1, vector2, Double::sum,
                (el1, el2) -> el1 * el2,
                (el1, el2) -> -(el1 * el2)
        );
    }

    private <T1, T2, R> void runParallel(T1[] v1, T2[] v2, BinaryOperator<R> aggOp, @SuppressWarnings("unchecked") BiFunction<T1, T2, R>... elOp) {
        final List<BiFunction<T1, T2, R>> operations = Arrays.asList(elOp);
        final List<R> results = Collections.synchronizedList(new ArrayList<>());
        final int numThreads = v1.length;
        
        barrier = new CyclicBarrier(numThreads, () -> 
            System.out.println("Ergebnis nach Zyklus " + results.size() / v1.length + ": " + results.stream().reduce(aggOp).get()));

        IntStream.range(0, numThreads).forEach(
            i -> new Thread(new ElementOperations<>(operations, v1[i], v2[i], results)).start());
    }

    record ElementOperations<T1, T2, R>(List<BiFunction<T1, T2, R>> operations, T1 element1, T2 element2, List<R> results) implements Runnable {
        @Override
        public void run() {
            operations.forEach(operation -> operation.andThen(this::handleAndOutput).apply(element1, element2));
        }

        private R handleAndOutput(R data) {
            results.add(data);
            System.out.println("Bearbeite Wert: " + data);
            
            try { barrier.await(); }
            catch (Exception e) { e.printStackTrace(); }
    
            return data;
        }
    }
}
