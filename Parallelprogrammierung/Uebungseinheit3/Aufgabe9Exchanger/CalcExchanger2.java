import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;

public class CalcExchanger2 {
    public void run() {
        final var exchanger = new Exchanger<Integer>();

        new Thread(() -> {
            try {
                // Exchanger ist sinnvoll, wenn clientseitige Datenmenge zu groß ist, um sie im Speicher
                // zu halten und daher Warten auf anderen Thread für nächste Eingabe vorzuziehen ist
                // (meist will man aber mehrere Producer/Consumer und es reicht unidirektionaler Austausch in
                // einem Schritt, weshalb man BlockingQueue usw. verwendet)

                // Aufgaben an anderen Thread weitergeben und vorherige Berechnungsergebnisse ausgeben
                for (int i=0; i<10; i++) {
                    final var number = ThreadLocalRandom.current().nextInt(1000);

                    outputResult(exchanger.exchange(number));

                    outputInput(number);
                }

                outputResult(exchanger.exchange(null)); // Anderen Thread beenden und letztes Ergebnis abholen

            } catch (InterruptedException e) {}
        }).start();

        // Incrementer thread
        new Thread(() -> {
            try {
                Integer data = null;
                while ((data = exchanger.exchange(data)) != null) {
                    data++;
                }
            } catch (InterruptedException e) {}
        }).start();
    }

    private void outputInput(Integer number) {
        if (number != null)
            System.out.print(number);
    }

    private void outputResult(Integer number) {
        if (number != null)
            System.out.println(" -> " + number);
    }
}