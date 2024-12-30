import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;
import java.util.function.Function;

public class CalcBarrier4 {
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};

        runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);

        // Syntax für Aufruf generischer Funktion unter Angabe der Typparameter
        // this.<Double>runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);
    }

    @SafeVarargs
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        runParallelOperation(vector, resultOperation, 0, elementOperations);
    }

    @SafeVarargs
    private <T> void runParallelOperation(T[] vector, Consumer<T[]> resultOperation, int operationIndex, Function<T, T> ...elementOperations) {
        final var nextOperationIndex = operationIndex + 1;

        // Barrier jeweils neu erzeugen zur Unterstützung einer unterschiedlichen Aggregatsfunktion während
        // der laufenden Operationen und am Ende
        CyclicBarrier barrier;
        if (nextOperationIndex == elementOperations.length)
            barrier = new CyclicBarrier(vector.length, new ResultOperation<>(vector, resultOperation));
        else
            barrier = new CyclicBarrier(vector.length,
                () -> runParallelOperation(vector, resultOperation, nextOperationIndex, elementOperations));

        for (int i=0; i<vector.length; i++)
            new Thread(new ElementOperation<>(elementOperations[operationIndex], vector, i, barrier)).start();
    }

    record ElementOperation<T>(Function<T, T> operation, T[] vector, int index, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            vector[index] = operation.apply(vector[index]);

            try { barrier.await(); }
            catch (Exception e) { e.printStackTrace(); }
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
