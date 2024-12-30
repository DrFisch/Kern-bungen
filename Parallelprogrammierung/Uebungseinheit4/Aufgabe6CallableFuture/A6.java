package Aufgabe6CallableFuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class A6 {
    public static void main(String[] args) {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        runParallel(vector, results -> System.out.println(Arrays.toString(results)),el -> el * el,el -> el + 4);
    }
    
    @SafeVarargs
    public static void runParallel(Double[] vector,  Consumer<Double[]> cons, Function<Double,  Double>... elementOperations) {

        ExecutorService exec=Executors.newCachedThreadPool();
        List<Future<Double>> futures = new ArrayList<>();

        for (var operation : elementOperations) {
            for (var value : vector) {
                futures.add(exec.submit(() -> operation.apply(value))); 
            }
        }

        List<Double> results = new ArrayList<>();
        try {
            for (Future<Double> future : futures) {
                results.add(future.get()); 
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        cons.accept(results.toArray(new Double[0]));

        exec.shutdown();
    }
    record ElementOperation<T1, T2, R>(Function<T1, R> operation, T1 element1,  
                                       List<R> results) implements Runnable {
        @Override
        public void run() {
            results.add(operation.apply(element1));
            
        }
    }
}
