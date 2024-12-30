import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;

public class CalcExchanger {
    public void run() {
        final var exchanger = new Exchanger<CalcData>();

        new Thread(() -> {
            try {
                // Exchanger ist sinnvoll, wenn clientseitige Datenmenge zu groß ist, um sie im Speicher
                // zu halten und daher Warten auf anderen Thread für nächste Eingabe vorzuziehen ist
                // (meist will man aber mehrere Producer/Consumer und es reicht unidirektionaler Austausch in
                // einem Schritt, weshalb man BlockingQueue usw. verwendet)

                // Aufgaben an anderen Thread weitergeben und vorherige Berechnungsergebnisse ausgeben
                for (int i=0; i<10; i++) {
                    final var number1 = ThreadLocalRandom.current().nextInt(1000);
                    final var number2 = ThreadLocalRandom.current().nextInt(1000);
                    final var operation = CalcOperation.values()[ThreadLocalRandom.current().nextInt(4)];

                    final var calcData = new CalcData(number1, number2, operation);

                    // Während ein neues calcData-Objekt übergeben wird, erhält man das
                    // zuvor übergebene samt Eingaben und Ergebnis im gleichen Schritt zurück
                    output(exchanger.exchange(calcData));
                }

                output(exchanger.exchange(null)); // Anderen Thread beenden und letztes Ergebnis abholen

            } catch (InterruptedException e) {}
        }).start();

        new Thread(() -> {
            try {
                CalcData data = null;
                while ((data = exchanger.exchange(data)) != null) {
                    data = data.withResult(switch (data.operation()) {
                        case ADD -> data.number1() + data.number2();
                        case SUBTRACT -> data.number1() - data.number2();
                        case MULTIPLY -> data.number1() * data.number2();
                        case DIVIDE -> data.number1() / data.number2();
                    });
                }
            } catch (InterruptedException e) {}
        }).start();
    }

    private void output(CalcData data) {
        if (data != null)
            System.out.println(data);
    }

    record CalcData(double number1, double number2, CalcOperation operation, double result) {
        CalcData(double number1, double number2, CalcOperation operation) {
            this(number1, number2, operation, Double.MIN_VALUE);
        }

        CalcData withResult(double result) {
            return new CalcData(number1, number2, operation, result);
        }
    }

    enum CalcOperation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}