package Uebungseinheit3.Aufgabe3RunParallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiFunction;

public class CalcBarrier3alternative {
    private CyclicBarrier barrier;
    private Double[] vector1;
    private Double[] vector2;
    private List<Double> results;
    private int operationIndex;
    private BiFunction<Double, Double, Double> aggregateOperation;
    private BiFunction<Double, Double, Double>[] elementOperations;

    @SuppressWarnings("unchecked")
    public void run() {
        this.vector1 = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        this.vector2 = new Double[]{4.0, 5.0, 6.0, 4.0, 5.0, 6.0};

        this.aggregateOperation = Double::sum;

        // Einschränkung in Java, da Annotation nur bei Deklaration möglich
        this.elementOperations = new BiFunction[]{
            (BiFunction<Double, Double, Double>) (el1, el2) -> el1 * el2,
            (BiFunction<Double, Double, Double>) (el1, el2) -> -(el1 * el2)
        };;

        this.results = Collections.synchronizedList(new ArrayList<>());
        this.operationIndex = 0;

        this.barrier = new CyclicBarrier(vector1.length, this::barrierAction);

        runParallelOperation();
    }

    private void runParallelOperation() {
        for (int i = 0; i < vector1.length; i++)
            new Thread(new ElementOperation(elementOperations[operationIndex], vector1[i], vector2[i], results, barrier)).start();
    }

    private void barrierAction() {
        operationIndex++;
        if (operationIndex < elementOperations.length)
            runParallelOperation();
        else
            new AggregateOperation(aggregateOperation, results).run();
    }

    record ElementOperation(BiFunction<Double, Double, Double> operation, Double element1, Double element2, List<Double> results, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            results.add(operation.apply(element1, element2));
            System.out.println(Arrays.toString(results.toArray()));
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    record AggregateOperation(BiFunction<Double, Double, Double> operation, List<Double> results) implements Runnable {
        @Override
        public void run() {
            if (results.size() == 0) {
                throw new IllegalArgumentException("List size must be greater than 0!");
            }

            Double sum = results.get(0);
            for (int i = 1; i < results.size(); i++) {
                sum = operation.apply(sum, results.get(i));
            }
            System.out.println("Final result: " + sum);
        }
    }
}
