

import java.util.function.Function;

public class A7 {
    // Die "outside" Methode, die eine Funktion zurückgibt
    public static Function<Integer, Integer> outside(final int x) {
        // Rückgabe einer anonymen Klasse, die Function implementiert
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer y) {
                return x + y; // Addiere den äußeren Wert mit dem inneren
            }
        };
    }
    public static void main(String[] args) {
        var fn_inside = outside(10);
        var result1a = fn_inside.apply(2);
        var result1b = outside(10).apply(2);
        System.out.println(result1a);
        System.out.println(result1b);
    }
}

