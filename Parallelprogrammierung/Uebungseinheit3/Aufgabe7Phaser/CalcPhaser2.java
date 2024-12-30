import java.util.Arrays;
import java.util.concurrent.Phaser;

public class CalcPhaser2 {
    // Ansatz ist für hochflexible bzw. dynamische Umgebungen mit mehreren Verarbeitungsschritten und
    // unterschiedlicher Threadzahl pro Phase geeignet, d.h. für Fork-Join-Szenarien mit komplexen Anforderungen
    private Phaser phaser = new Phaser() {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            System.out.println(String.format("Phase %d - vector: %s", phase, getVectorAsString()));
            return super.onAdvance(phase, registeredParties);
        }

        private String getVectorAsString() {
            return Arrays.toString(Arrays.stream(vector).filter(el -> el != Double.MIN_VALUE).toArray());
        }
    };
    private double[] vector = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0};

    public void run() {
        System.out.println("Main registered.");
        phaser.register(); // Main Thread registrieren

        // Start phase 1
        new Thread(new CalcParticipant(vector, 0, 2, phaser)).start();
        new Thread(new CalcParticipant(vector, 2, 4, phaser)).start();
        new Thread(new CalcParticipant(vector, 4, 6, phaser)).start();
        phaser.arriveAndAwaitAdvance();

        // Start phase 2
        new Thread(new CalcParticipant(vector, 0, 6, phaser)).start();
        phaser.arriveAndAwaitAdvance();

        // Start phase 3 (required to transition to phase 2 und call onAdvance to output result)
        phaser.arriveAndAwaitAdvance();
    }

    record CalcParticipant(double[] vector, int startIndex, int endIndex, Phaser phaser) implements Runnable {
        CalcParticipant {
            phaser.register();

            // Initialisierung in Konstruktor noch nicht abgeschlossen, deshalb Konstruktorparameter statt Members ausgeben
            System.out.println(name(startIndex, endIndex) + " registered.");
        }

        public String name() {
            return name(startIndex, endIndex);
        }

        public String name(int startIndex, int endIndex) {
            return String.format("CalcParticipant-for-vector[%d,%d]", startIndex, endIndex);
        }

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            System.out.println(name() + " starts doing its first job...");

            try {
                // Hier komplexe Berechnung durchführen
                Thread.sleep(100);

                for (int i=startIndex+1; i<endIndex; i++) {
                    if (vector[i] != Double.MIN_VALUE) {
                        vector[startIndex] += vector[i];
                        vector[i] = Double.MIN_VALUE;
                    }
                }

            } catch (InterruptedException e) { }

            System.out.println(name() + " finished first job and deregisters...");
            phaser.arriveAndDeregister();
        }
    }
}
