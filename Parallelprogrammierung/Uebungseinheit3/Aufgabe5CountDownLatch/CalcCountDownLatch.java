package Uebungseinheit3.Aufgabe5CountDownLatch;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Function;

public class CalcCountDownLatch {
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};

        runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);
    }

    @SafeVarargs
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        CountDownLatch operationsCountDownLatch = new CountDownLatch(elementOperations.length * vector.length);

        for (int i=0; i<vector.length; i++)
            new Thread(new ElementOperation<>(elementOperations, vector, i, operationsCountDownLatch)).start();

        try {
            operationsCountDownLatch.await();
            resultOperation.accept(vector);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    record ElementOperation<T>(Function<T, T>[] operations, T[] vector, int index, CountDownLatch operationsCountDownLatch) implements Runnable {
        @Override
        public void run() {
            int i = 1;
            for (final var operation : operations) {
                vector[index] = operation.apply(vector[index]);
                operationsCountDownLatch.countDown();

                System.out.println(String.format("%s: %d/%d done", Thread.currentThread().threadId(),
                        i++, operations.length));
            }
        }
    }
}
