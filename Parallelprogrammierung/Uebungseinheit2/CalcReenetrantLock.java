package Uebungseinheit2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class CalcReenetrantLock {
    public void run() {
        final var sharedState = new SharedState(1);

        double start = System.nanoTime();

        List<CalcServer> calcServerThreads = new ArrayList<CalcServer>();

        // Create CalcServer threads
        for (int i=0; i<10; i++)
            calcServerThreads.add(new CalcServer(sharedState));

        // Start CalcServer thrads
        calcServerThreads.forEach( thread -> thread.start());

        // Wait for CalcServer threads to finish
        calcServerThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        double end = System.nanoTime();

        System.out.println((end-start) / 1000 / 1000 + " ms");
    }

    class CalcServer extends Thread {
        private SharedState state;
        public CalcServer(SharedState state) {
            this.state = state;
        }

        @Override
        public void run() {
            for (int i=0; i<4; i++) {
                final var value = ThreadLocalRandom.current().nextDouble(1000);
                final var operation = CalcOperation.values()[ThreadLocalRandom.current().nextInt(4)];

                state.beginTransaction();
                final var oldValue = state.getValue();
                state.apply(operation, value);
                final var newValue = state.getValue();
                state.endTransaction();

                System.out.println(String.format("%s: %s(%.2f, %.2f) = %.2f (%b)",
                        Thread.currentThread().getId(), operation, oldValue, value, newValue,
                        newValue == CalcOperation.apply(operation, oldValue, value)));
            }
        }
    }

    class SharedState {
        private ReentrantLock lock = new ReentrantLock();

        private double value = 0;

        public SharedState(double initialValue) {
            this.value = initialValue;
        }

        public void beginTransaction() {
            lock.lock();
        }

        public double getValue() {
            return value;
        }

        public void apply(CalcOperation op, double v) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            value = CalcOperation.apply(op, value, v);
        }

        public void endTransaction() {
            lock.unlock();
        }
    }

    enum CalcOperation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE;

        public static double apply(CalcOperation op, double number1, double number2) {
            return switch (op) {
                case ADD -> number1 + number2;
                case SUBTRACT -> number1 - number2;
                case MULTIPLY -> number1 * number2;
                case DIVIDE -> number1 / number2;
            };
        }
    }
}
