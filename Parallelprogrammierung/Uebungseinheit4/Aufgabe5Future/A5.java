package Aufgabe5Future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Consumer;


public class A5 {
    public static void main(String[] args) {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        runParallel(vector, results -> System.out.println(Arrays.toString(results)),el -> el * el,el -> el + 4);
        System.out.println("hi1");
    }
    
    @SafeVarargs
    public static void runParallel(Double[] vector,  Consumer<Double[]> cons, Function<Double,  Double>... elementOperations) {

        ExecutorService exec = Executors.newCachedThreadPool();
        Future<?>[] futures = new Future[vector.length];

        // Starte Aufgaben mit Runnable und verwende Future
        for (var operation : elementOperations) {
            for (int i=0;i<vector.length;i++) {
               
                futures[i]=exec.submit(new ElementOperation(operation, i, vector));

                
            }
        }
        
        List.of(futures).forEach((el)->{
            try { el.get(); } // result.get() gibt hier immer null zurück, da Runnable und kein Callable implementiert wurde
            catch (Exception e) { e.printStackTrace(); }
        });

        cons.accept(vector);

        exec.shutdown();
    }
    record ElementOperation<T1, T2, R>(Function<T1, T1> operation, int index,  
                                       T1[] vector) implements Runnable {
        @Override
        public void run() {
            vector[index]=operation.apply(vector[index]);
            
        }
    }
}
