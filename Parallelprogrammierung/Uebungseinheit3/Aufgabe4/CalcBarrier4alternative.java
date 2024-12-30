import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;
import java.util.function.Function;

public class CalcBarrier4alternative {
    private CyclicBarrier barrier;
    private Double[] vector;
    private Consumer<Double[]> resultOperation;
    private Function<Double, Double>[] elementOperations;
    private int operationIndex;

    @SuppressWarnings("unchecked")
    public void run() {
        this.vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        this.resultOperation = results -> System.out.println(Arrays.toString(results));
        this.elementOperations = new Function[]{
            (Function<Double, Double>) (el -> el * el),
            (Function<Double, Double>) (el -> el + 4)
        };

        this.operationIndex = 0;
        this.barrier = new CyclicBarrier(vector.length, this::barrierAction);

        runParallelOperation();
    }

    private void runParallelOperation() {
        for (int i = 0; i < vector.length; i++) {
            new Thread(new ElementOperation<>(elementOperations[operationIndex], vector, i, barrier)).start();
        }
    }

    private void barrierAction() {
        operationIndex++;
        if (operationIndex < elementOperations.length) {
            runParallelOperation();
        } else {
            new ResultOperation<>(vector, resultOperation).run();
        }
    }

    record ElementOperation<T>(Function<T, T> operation, T[] vector, int index, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            vector[index] = operation.apply(vector[index]);

            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    record ResultOperation<R>(R[] results, Consumer<R[]> operation) implements Runnable {
        @Override
        public void run() {
            if (results.length == 0) {
                throw new IllegalArgumentException("Array size must be greater than 0!");
            }

            operation.accept(results);
        }
    }
}