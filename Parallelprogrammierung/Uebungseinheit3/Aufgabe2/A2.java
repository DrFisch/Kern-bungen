package Uebungseinheit3.Aufgabe2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiFunction;

public class A2 {
    public static void main(String[] args) {
        new A2().run();
    }
    public void run() {
        var vector1 = new int[]{1, 2, 3, 1, 2, 3};
        var vector2 = new int[]{4, 5, 6, 4, 5, 6};

        // Einfache ArrayList reicht nicht, weil bei parallelen Add-Zugriffen teils einige verlorengehen
        // bzw. andere überschreiben wegen interner Implementierung
        var results = Collections.synchronizedList(new ArrayList<Integer>());
        var barrier = new CyclicBarrier(vector1.length, new AggregateOperation<>((x,y)->x+y, results));

        for (int i=0; i<vector1.length; i++)
            new Thread(new ElementOperation<>((x,y)->x*y, vector1[i], vector2[i], results, barrier)).start();
    }


    record ElementOperation<T>(BiFunction<T,T,T> func, T element1, T element2, List<T> results, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            results.add(func.apply(element1, element2));

            System.out.println(Arrays.toString(results.toArray()));
            try { barrier.await(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    record AggregateOperation<R>(BiFunction<R,R,R> func, List<R> results) implements Runnable {
        @Override
        public void run() {
            if (results.size() == 0)
                throw new IllegalArgumentException("List size must be greater than 0!");

            //R sum = results.get(0);

            
            // auch möglich mit stream().reduce()
            R sum = results.stream().reduce((x, y) -> func.apply(x, y)).get();

            // for (int i=1; i<results.size(); i++)
            //     sum = func.apply(sum, results.get(i));

            System.out.println("Result: " + sum);
        }
    }
}