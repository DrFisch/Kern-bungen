package A7;

import java.util.function.Function;

public class A7 {
    public static void main(String[] args) {
        // Implementieren Sie alle benötigten Konstrukte, damit der folgende Code die
        // dargestellte Ausgabe erzeugt. Realisieren Sie Ihre Lösung mit und ohne Lambda-Ausdruck.
        var fn_inside = outside(10);
        var result1a = fn_inside.apply(2);
        var result1b = outside(10).apply(2);
        System.out.println(result1a);
        System.out.println(result1b);
    }
    public static Function<Integer, Integer> outside(int zahl){
        // return x -> zahl+x;

        return new Function<Integer,Integer>(){
            @Override
            public Integer apply(Integer i){
                return i+zahl;
            }
        };
    }
}
