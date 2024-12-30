import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class CalcBarrier {
    public void run() {
        var vector1 = new double[]{1, 2, 3, 1, 2, 3};
        var vector2 = new double[]{4, 5, 6, 4, 5, 6};

        // Einfache ArrayList reicht nicht, weil bei parallelen Add-Zugriffen teils einige verlorengehen
        // bzw. andere überschreiben wegen interner Implementierung
        var results = Collections.synchronizedList(new ArrayList<Double>());
        var barrier = new CyclicBarrier(vector1.length, new AggregateOperation(CalcOperation.ADD, results));

        for (int i=0; i<vector1.length; i++)
            new Thread(new ElementOperation(CalcOperation.MULTIPLY, vector1[i], vector2[i], results, barrier)).start();
    }

//    enum CalcOperation {
//        ADD {
//            @Override
//            public double apply(double number1, double number2) {
//                return number1 + number2;
//            }
//        },
//        SUBTRACT {
//            @Override
//            public double apply(double number1, double number2) {
//                return number1 - number2;
//            }
//        },
//        MULTIPLY {
//            @Override
//            public double apply(double number1, double number2) {
//                return number1 * number2;
//            }
//        },
//        DIVIDE {
//            @Override
//            public double apply(double number1, double number2) {
//                return number1 / number2;
//            }
//        };
//
//        public abstract double apply(double number1, double number2);
//    }

//    enum CalcOperation {
//        ADD, SUBTRACT, MULTIPLY, DIVIDE;
//
//        public static double apply(CalcOperation op, double number1, double number2) {
//            return switch (op) {
//                case ADD -> number1 + number2;
//                case SUBTRACT -> number1 - number2;
//                case MULTIPLY -> number1 * number2;
//                case DIVIDE -> number1 / number2;
//            };
//        }
//    }

    interface CalcOperation {
        double apply(double number1, double number2);

        CalcOperation ADD = (number1, number2) -> number1 + number2;
        CalcOperation SUBTRACT = (number1, number2) -> number1 - number2;
        CalcOperation MULTIPLY = (number1, number2) -> number1 * number2;
        CalcOperation DIVIDE = (number1, number2) -> number1 / number2;
    }

    record ElementOperation(CalcOperation operation, double element1, double element2, List<Double> results, CyclicBarrier barrier) implements Runnable {
        @Override
        public void run() {
            results.add(operation.apply(element1, element2));

            System.out.println(Arrays.toString(results.toArray()));
            try { barrier.await(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    record AggregateOperation(CalcOperation pensi, List<Double> results) implements Runnable {
        @Override
        public void run() {
            if (results.size() == 0)
                throw new IllegalArgumentException("List size must be greater than 0!");

            Double sum = results.get(0);

            for (int i=1; i<results.size(); i++)
                sum = pensi.apply(sum, results.get(i));

            System.out.println("Result: " + sum);
        }
    }
}
