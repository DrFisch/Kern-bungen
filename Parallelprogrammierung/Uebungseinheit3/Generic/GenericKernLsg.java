

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiFunction;
 
public class GenericKernLsg {
    public static void main(String[] args) {
        new GenericKernLsg().run();
    }
    public void run() {
        var vector1 = new int[]{1, 2, 3, 1, 2, 3};
        var vector2 = new int[]{4, 5, 6, 4, 5, 6};
 
        var results = Collections.synchronizedList(new ArrayList<Integer>());
        var barrier = new CyclicBarrier(vector1.length, new AggregateOperation<>((x,y)->x+y, results));
 
        for (int i=0; i<vector1.length; i++)
            new Thread(new ElementOperation<>((el1, el2) -> el1 * el2, vector1[i], vector2[i], results, barrier)).start();
    }
 
    record ElementOperation<T1, T2, R>(BiFunction<T1, T2, R> operation, T1 element1, T2 element2, List<R> results, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            results.add(operation.apply(element1, element2));
            System.out.println(Arrays.toString(results.toArray()));
            try { barrier.await(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
 
    record AggregateOperation<R>(BiFunction<R, R, R> operation, List<R> results) implements Runnable {
        @Override
        public void run() {
            if (results.size() == 0)
                throw new IllegalArgumentException("List size must be greater than 0!");
 
            R sum = results.get(0);
 
            for (int i=1; i<results.size(); i++)
                sum = operation.apply(sum, results.get(i));
 
            System.out.println("Result: " + sum);
        }
    }
}
 