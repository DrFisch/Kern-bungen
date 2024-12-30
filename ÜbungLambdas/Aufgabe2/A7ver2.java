

import java.util.function.Function;

public class A7ver2 {
    public static void main(String[] args) {
        
    }
    public static Function<Integer, Integer> outside2(final int x) {
        // Rückgabe einer anonymen Klasse, die Function implementiert
        return (Integer y) -> x+y;
    }
}
