package Uebungseinheit3.EnumOperationDef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class A1Barrier2 {
    public static void main(String[] args) {
        var vector1 = new double[]{1, 2, 3, 1, 2, 3};
        var vector2 = new double[]{4, 5, 6, 4, 5, 6};

        // Synchronisierte Liste für Ergebnisse
        var results = Collections.synchronizedList(new ArrayList<Double>());

        // CyclicBarrier mit AggregateOperation als finaler Aktion
        var barrier = new CyclicBarrier(
            vector1.length,
            new AggregateOperation(CalcOperation.ADD, results) // Aggregation nach Abschluss aller Threads
        );

        // Threads für elementweise Operationen
        for (int i = 0; i < vector1.length; i++) {
            new Thread(new ElementOperation(
                CalcOperation.MULTIPLY,
                vector1[i],
                vector2[i],
                results,
                barrier)
            ).start();
        }
    }
}

// Klasse für die Aggregatsoperation
class AggregateOperation implements Runnable {
    private final CalcOperation operation;
    private final List<Double> results;

    public AggregateOperation(CalcOperation operation, List<Double> results) {
        this.operation = operation;
        this.results = results;
    }

    @Override
    public void run() {
        synchronized (results) {
            double aggregateResult = results.stream()
                                            .reduce(operation.identity(), operation::apply);

            System.out.println("Aggregate Result (" + operation + "): " + aggregateResult);
        }
    }
}

// Klasse für die elementweise Berechnung
class ElementOperation implements Runnable {
    private final CalcOperation operation;
    private final double value1;
    private final double value2;
    private final List<Double> results;
    private final CyclicBarrier barrier;

    public ElementOperation(CalcOperation operation, double value1, double value2, List<Double> results, CyclicBarrier barrier) {
        this.operation = operation;
        this.value1 = value1;
        this.value2 = value2;
        this.results = results;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        // Elementweise Operation direkt aus CalcOperation
        double result = operation.apply(value1, value2);

        // Ergebnis hinzufügen
        results.add(result);

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
    ADD {
        @Override
        public double apply(double a, double b) {
            return a + b;
        }

        @Override
        public double identity() {
            return 0; // Neutrales Element für Addition
        }
    },
    MULTIPLY {
        @Override
        public double apply(double a, double b) {
            return a * b;
        }

        @Override
        public double identity() {
            return 1; // Neutrales Element für Multiplikation
        }
    },
    SUBTRACT {
        @Override
        public double apply(double a, double b) {
            return a - b;
        }

        @Override
        public double identity() {
            return 0; // Subtraktionsaggregation beginnt bei 0
        }
    },
    DIVIDE {
        @Override
        public double apply(double a, double b) {
            return a / b;
        }

        @Override
        public double identity() {
            return 1; // Division startet bei 1
        }
    };

    // Abstrakte Methode für die Berechnung
    public abstract double apply(double a, double b);

    // Abstrakte Methode für das neutrale Element (Identität)
    public abstract double identity();
}
