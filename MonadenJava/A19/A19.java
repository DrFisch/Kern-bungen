package A19;

import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

public class A19 {
    public static void main(String[] args) {
        int[] v1 =IntStream.range(1, 101).toArray();
        //int[] v2 = new int[100]; Arrays.setAll(v2,i -> i+1);

        var karte =Arrays.stream(v1).boxed().collect(Collectors.toMap(k->k, v->v*v));
        karte.forEach((key,value )-> System.out.println("Key: "+key+" , Value: "+ value));
        //System.out.println(karte);
        
    }
}
// Übung 19: Erstellen Sie eine Map mit allen Zahlen von 1 bis 100 als Keys sowie den
// zugehörigen Quadratzahlen als Values. Erzeugen Sie als Zwischenschritt ein Array
// mit den Zahlen von 1 bis 100 (ohne alle einzelnen Werte selbst vorzugeben).
// List<Integer> l = List.of(1, 2, 3, 4);
// var result = … // 1, -1, 2, -2, 3, -3, 4, -4