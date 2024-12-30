package Aufgabe7CompletableFuture;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class A7 {
    public static void main(String[] args) {
        new CalcFuture3().run();
    }
}


class CalcFuture3 {
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};

        runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);
    }

    @SafeVarargs
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        ExecutorService exec = Executors.newCachedThreadPool();

        CompletableFuture<?>[] results = new CompletableFuture[vector.length];

        for (int operationIndex=0; operationIndex<elementOperations.length; operationIndex++) {
            for (int i=0; i<vector.length; i++)
                if (results[i] == null)
                    results[i] = CompletableFuture.runAsync(new ElementOperation<>(elementOperations[operationIndex], vector, i), exec);
                else
                    results[i] = results[i].thenRunAsync(new ElementOperation<>(elementOperations[operationIndex], vector, i), exec);
        }

        CompletableFuture.allOf(results)
            .thenRunAsync(new ResultOperation<>(vector, resultOperation), exec)
            .thenRun(exec::shutdown)
            .join();
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
