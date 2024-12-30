package A8;

import java.util.Arrays;


public class A8 {
    public static void main(String[] args) {
        // Übung 8: Übersetzen Sie folgenden Code in ein imperatives Äquivalent, bei dem
        // ohne Streams-API manuell über for-Schleifen, Abfragen und Hilfsdatenstrukturen
        // gearbeitet wird.

        var arr = new String[]{"Chris", "Alex", "Mike"};
        Arrays.stream(arr)
            .map(item -> item.toLowerCase()) // map auf to lower case
            .filter(item -> item.contains("i")) // Filter
            .map(item -> item + " " + item.length()) // map item auf länge
            .forEach(item -> System.out.println(item)); // print for each

        System.out.println();

        for (String s : arr) {
            String slow = s.toLowerCase();
            if(slow.contains("i")){
                System.out.println(slow + " " + slow.length());
            }
        }
    }
}
