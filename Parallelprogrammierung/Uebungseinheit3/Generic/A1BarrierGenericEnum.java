

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class A1BarrierGenericEnum {
    public static void main(String[] args) {
        var vector1 = List.of(1, 2, 3, 1, 2, 3);
        var vector2 = List.of(4, 5, 6, 4, 5, 6);

        // Synchronisierte Liste für Ergebnisse
        var results = Collections.synchronizedList(new ArrayList<Double>());

        // CyclicBarrier mit AggregateOperation als finaler Aktion
        var barrier = new CyclicBarrier(
            vector1.size(),
            new AggregateOperation<>(CalcOperation.ADD, results) // Aggregation nach Abschluss aller Threads
        );

        // Threads für elementweise Operationen
        for (int i = 0; i < vector1.size(); i++) {
            new Thread(new ElementOperation<>(
                CalcOperation.MULTIPLY,
                vector1.get(i),
                vector2.get(i),
                results,
                barrier)
            ).start();
        }
    }
}

// Klasse für die Aggregatsoperation
class AggregateOperation<T extends Number> implements Runnable {
    private final CalcOperation operation;
    private final List<T> results;

    public AggregateOperation(CalcOperation operation, List<T> results) {
        this.operation = operation;
        this.results = results;
    }

    @Override
    public void run() {
        synchronized (results) {
            if (results.isEmpty()) {
                System.out.println("No results to aggregate.");
                return;
            }

            double aggregateResult = (operation == CalcOperation.MULTIPLY) ? 1.0 : 0.0;
            for (T result : results) {
                switch (operation) {
                    case ADD -> aggregateResult += result.doubleValue();
                    case MULTIPLY -> aggregateResult *= result.doubleValue();
                    case SUBTRACT -> aggregateResult -= result.doubleValue();
                    case DIVIDE -> aggregateResult /= result.doubleValue();
                }
            }

            System.out.println("Aggregate Result (" + operation + "): " + aggregateResult);
        }
    }
}

// Klasse für die elementweise Berechnung
class ElementOperation<T extends Number> implements Runnable {
    private final CalcOperation operation;
    private final T value1;
    private final T value2;
    private final List<Double> results;
    private final CyclicBarrier barrier;

    public ElementOperation(CalcOperation operation, T value1, T value2, List<Double> results, CyclicBarrier barrier) {
        this.operation = operation;
        this.value1 = value1;
        this.value2 = value2;
        this.results = results;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        double result = 0;

        // Elementweise Operation
        switch (operation) {
            case ADD -> result = value1.doubleValue() + value2.doubleValue();
            case MULTIPLY -> result = value1.doubleValue() * value2.doubleValue();
            case SUBTRACT -> result = value1.doubleValue() - value2.doubleValue();
            case DIVIDE -> result = value1.doubleValue() / value2.doubleValue();
        }

        // Ergebnis hinzufügen
        synchronized (results) {
            results.add(result);
        }

        System.out.println("Thread completed operation: " + result);

        // Warten an der Barriere
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted or barrier broken.");
        }
    }
}

// Enum für die Rechenoperationen
enum CalcOperation {
    ADD, MULTIPLY, SUBTRACT, DIVIDE;
}
