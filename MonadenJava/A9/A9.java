package A9;

import java.util.List;
import java.util.stream.Collectors;

public class A9 {
    //Übung 9: Was gibt folgender Code aus?
    public static void main(String[] args) {
        var numbers = List.of("one", "two", "three", "four",
            "five", "six");
        System.out.println(numbers.stream().filter(
        it -> it.length()>5).collect(Collectors.toList())); // Ausgabe leer
    }

}
