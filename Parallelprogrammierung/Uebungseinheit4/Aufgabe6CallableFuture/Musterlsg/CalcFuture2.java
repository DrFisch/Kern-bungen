package Aufgabe6CallableFuture.Musterlsg;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class CalcFuture2 {
    public static void main(String[] args) {
        new CalcFuture2().run();
    }
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        System.out.println("This is a test");
        runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        ExecutorService exec = Executors.newCachedThreadPool();

        Future<T>[] results = new Future[vector.length];

        for (int operationIndex=0; operationIndex<elementOperations.length; operationIndex++) {
            for (int i=0; i<vector.length; i++)
                results[i] = exec.submit(new ElementOperation<>(elementOperations[operationIndex], vector, i));

            // Auf Ergebnis warten
            List.of(results).forEach(result -> {
                try { System.out.println("Teilergebnis: " + result.get()); }
                catch (Exception e) { e.printStackTrace(); }
            });
        }

        try { exec.submit(new ResultOperation<>(vector, resultOperation)).get(); }
        catch (Exception e) { e.printStackTrace(); }

        exec.shutdown();
    }

    record ElementOperation<T>(Function<T, T> operation, T[] vector, int index) implements Callable<T> {
        @Override
        public T call() {
            vector[index] = operation.apply(vector[index]); return vector[index];
        }
    }

    record ResultOperation<R>(R[] results, Consumer<R[]> operation) implements Runnable {
        @Override
        public void run() {
            if (results.length == 0)
                throw new IllegalArgumentException("Array size must be greater than 0!");

            operation.accept(results);
        }
    }
}
