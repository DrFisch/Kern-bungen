import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Function;
import java.util.stream.IntStream;

public class CalcBarrier4alternative2 {
    @SuppressWarnings("unchecked")
    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        
        var elementOperations = new Function[]{
            (Function<Double, Double>) (el -> el * el),
            (Function<Double, Double>) (el -> el + 4)
        };

        var barrier = new CyclicBarrier(vector.length,
            () -> System.out.println(Arrays.toString(vector)));

        runParallelOperation(vector, elementOperations, barrier);
    }

    private void runParallelOperation( Double[] vector, Function<Double, Double>[] elementOperations, CyclicBarrier barrier) {
        IntStream.range(0, vector.length)
            .forEach(i -> new Thread(new ElementOperation<>(elementOperations, vector, i, barrier)).start());
    }

    record ElementOperation<T>(Function<T, T>[] operations, T[] vector, int index, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            Arrays.stream(operations).forEach(operation -> vector[index] = operation.apply(vector[index]));
            
            try { barrier.await(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}