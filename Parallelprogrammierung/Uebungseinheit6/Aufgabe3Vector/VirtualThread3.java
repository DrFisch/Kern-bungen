package Uebungseinheit6.Aufgabe3Vector;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.function.*;

public class VirtualThread3 {
    private double[] arrayOperation(double[] vector1, double[] vector2, CalcOperation operation) throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var futures = IntStream.range(0, vector1.length)
                    .mapToObj(index -> ((Callable<Double>)() -> operation.apply(vector1[index], vector2[index])))
                    .map(scope::fork).toList();

            scope.join();
            scope.throwIfFailed();

            return futures.stream().map(Supplier::get).mapToDouble(Double::doubleValue).toArray();
        }
    }

    private Double aggregateOperation(double[] vector, CalcOperation operation) throws ExecutionException, InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return executor.submit(() -> Arrays.stream(vector).reduce((left, right) -> operation.apply(left, right))).get().getAsDouble();
        }
    }

    public void run() {
        var vector1 = new double[]{1, 2, 3, 1, 2, 3};
        var vector2 = new double[]{4, 5, 6, 4, 5, 6};

        try {
            var resultArray = arrayOperation(vector1, vector2, CalcOperation.MULTIPLY);
            var result = aggregateOperation(resultArray, CalcOperation.ADD);
            System.out.println(Arrays.toString(resultArray));
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    interface CalcOperation {
        double apply(double number1, double number2);

        CalcOperation ADD = (number1, number2) -> number1 + number2;
        CalcOperation SUBTRACT = (number1, number2) -> number1 - number2;
        CalcOperation MULTIPLY = (number1, number2) -> number1 * number2;
        CalcOperation DIVIDE = (number1, number2) -> number1 / number2;
    }
}
