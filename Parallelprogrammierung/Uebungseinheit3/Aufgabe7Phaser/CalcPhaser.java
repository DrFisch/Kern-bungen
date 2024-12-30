import java.util.Arrays;
import java.util.concurrent.Phaser;
import java.util.function.Consumer;
import java.util.function.Function;

public class CalcPhaser {

    public void run() {
        var vector = new Double[]{1.0, 2.0, 3.0, 1.0, 2.0, 3.0};
      
        runParallel(vector, results -> System.out.println(Arrays.toString(results)), el -> el * el, el -> el + 4);
    }

    @SafeVarargs
    private <T> void runParallel(T[] vector, Consumer<T[]> resultOperation, Function<T, T> ...elementOperations) {
        // Verwendung von 2 Phasern, damit von diesem Thread aus zunächst immer einleitender Text zur
        // aktuellen Operation ausgegeben wird, bevor nächste Operation auf allen Threads gestartet wird
        // (vector.length + 1), da für jedes Element Thread arrived, es aber nicht gleich losgehen soll, sondern
        // erst wenn durch den folgenden Code ein (n+1). Mal arrived wird
        Phaser operationsPhaser = new Phaser(vector.length+1);
        Phaser outputPhaser = new Phaser(vector.length+1);

        for (int i=0; i<vector.length; i++)
            new Thread(new ElementOperation<>(elementOperations, vector, i, operationsPhaser, outputPhaser)).start();

        // Durchführung der anderen Threads koordinieren
        for (int i=0; i<elementOperations.length; i++) {
            System.out.println(String.format("Execute operation %d on all threads", i));
            operationsPhaser.arriveAndAwaitAdvance(); // Nächste Operation / Phase in allen Threads starten

            // Warten bis Ergebnisse von aktueller Operation von allen Threads ausgegeben wurden und erst dann
            // hier nächste Iteration starten und Text für nächste Operation ausgeben, bevor dann wieder alle
            // Threads nächste Operation ausführen
            outputPhaser.arriveAndAwaitAdvance();
        }

        resultOperation.accept(vector);
    }

    record ElementOperation<T>(Function<T, T>[] operations, T[] vector, int index, Phaser operationsPhaser, Phaser outputPhaser) implements Runnable {
        @Override
        public void run() {
            // Separater Index, da operationsCountDownLatch.getCount() nicht synchron ausgeführt wird und Ergebnis
            // daher bei mehreren Threads nicht sinnvoll ist
            int i = 1;
            for (final var operation : operations) {
                operationsPhaser.arriveAndAwaitAdvance();
                vector[index] = operation.apply(vector[index]);

                System.out.println(String.format("Thread %s: %d/%d done", Thread.currentThread().threadId(),
                        i++, operations.length));

                outputPhaser.arriveAndAwaitAdvance();
            }
        }
    }
}
