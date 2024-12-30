

import java.util.function.Consumer;

@FunctionalInterface
interface Consumer2 extends Consumer<String> {
    default void consume(String str, int count) {
        for (int i = 0; i < count; i++) {
            accept(str);
        }
    }
}

public class A2 {
    public static void main(String[] args) {
        Consumer2 x = System.out::print;

        // Beispiel-Aufrufe
        x.consume("Hallo", 1);  // Ausgabe: Hallo
        x.consume("o", 5);      // Ausgabe: ooooo
    }
}
