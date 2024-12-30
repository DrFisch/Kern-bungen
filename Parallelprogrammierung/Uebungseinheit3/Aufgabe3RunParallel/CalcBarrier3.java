package Uebungseinheit3.Aufgabe3RunParallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiFunction;

public class CalcBarrier3 {
    public void run() {
        var vector1 = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
        var vector2 = new Double[]{4.0, 5.0, 6.0, 4.0, 5.0, 6.0};

        // Mehrere Rechenoperationen jeweils auf 2 Vektoren ausführen, z.B. alle Werte der 2 Vektoren
        // multiplizieren und auch unabhängig davon addieren und am Ende alle Teilergebnisse aufsummieren
        runParallel(vector1, vector2, Double::sum, (el1, el2) -> el1 * el2, (el1, el2) -> -(el1 * el2));

        // Syntax für Aufruf generischer Funktion unter Angabe der Typparameter
        // this.<Double, Double, Double>runParallel(vector1, vector2, Double::sum, (el1, el2) -> el1 * el2);
    }

    @SafeVarargs
    private <T1, T2, R> void runParallel(T1[] vector1, T2[] vector2,
                             BiFunction<R, R, R> aggregateOperation, BiFunction<T1, T2, R> ...elementOperations) {
        var results = Collections.synchronizedList(new ArrayList<R>());

        runParallelOperation(vector1, vector2, results, 0,
                aggregateOperation,
                elementOperations);
    }

    @SafeVarargs
    private <T1, T2, R> void runParallelOperation(T1[] vector1, T2[] vector2, List<R> results, int operationIndex,
                                      BiFunction<R, R, R> aggregateOperation, BiFunction<T1, T2, R> ...elementOperations) {
        final var nextOperationIndex = operationIndex + 1;

        // Barrier jeweils neu erzeugen zur Unterstützung einer unterschiedlichen Aggregatsfunktion während
        // der laufenden Operationen und am Ende
        CyclicBarrier barrier;
        if (nextOperationIndex == elementOperations.length)
            barrier = new CyclicBarrier(vector1.length, new AggregateOperation<>(aggregateOperation, results));
        else
            barrier = new CyclicBarrier(vector1.length, () -> runParallelOperation(vector1, vector2, results, nextOperationIndex,
                        aggregateOperation, elementOperations));

        // N Threads starten für Element-Operationen, wobei wenn alle fertig sind, dann 1x runParallelOperation
        // (oder AggregateOperation, wenn bereits letzte Operation) aufgerufen wird
        for (int i=0; i<vector1.length; i++)
            new Thread(new ElementOperation<>(elementOperations[operationIndex], vector1[i], vector2[i], results, barrier)).start();
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
