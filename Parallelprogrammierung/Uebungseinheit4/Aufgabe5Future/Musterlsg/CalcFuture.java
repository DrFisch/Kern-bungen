package Aufgabe5Future.Musterlsg;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class CalcFuture {
    public static void main(String[] args) {
        new CalcFuture().run();
    }
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        System.out.println("test");
        runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);
        // [5.0, 8.0, 13.0, 5.0, 8.0, 13.0]
    }

    @SafeVarargs
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        ExecutorService exec = Executors.newCachedThreadPool();

        // Runnable returned keinen Wert, d.h. supply gibt unbestimmtes Future<?> zurück
        Future<?>[] results = new Future[vector.length];

        for (int operationIndex=0; operationIndex<elementOperations.length; operationIndex++) {
            for (int i=0; i<vector.length; i++) {
                 results[i] = exec.submit(new ElementOperation<>(elementOperations[operationIndex], vector, i));

                // Alternative
//                int finalOperationIndex = operationIndex;
//                int finalI = i;
//                results[i] = exec.submit(() -> vector[finalI] = elementOperations[finalOperationIndex].apply(vector[finalI]));
            }

            // Auf Ergebnis warten
            List.of(results).forEach(result -> {
                try { result.get(); } // result.get() gibt hier immer null zurück, da Runnable und kein Callable implementiert wurde
                catch (Exception e) { e.printStackTrace(); }
            });

            // Alternative mit Polling
//            while (true) {
//                for (var result : results)
//                    if (!result.isDone()) {
//                        Thread.yield();
//                        continue;
//                    }
//
//                break;
//            }
        }

        try { exec.submit(new ResultOperation<>(vector, resultOperation)).get(); }
        catch (Exception e) { e.printStackTrace(); }

        exec.shutdown();
    }

    record ElementOperation<T>(Function<T, T> operation, T[] vector, int index) implements Runnable {
        @Override
        public void run() {
            vector[index] = operation.apply(vector[index]);
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
