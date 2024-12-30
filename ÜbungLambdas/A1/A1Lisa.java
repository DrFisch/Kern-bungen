package A1;

import java.util.function.Consumer;

interface Konsumierer<T>  {
    void essen(T food);
}

public class A1Lisa {
    public static void main(String[] args) {
        // a)
        Konsumierer<String> k=x ->System.out.println(x);
        k.essen("Ich esse G's");

        // b)
        Consumer c = y ->System.out.println(y);
        c.accept("Pensi");

        // c)
        var v =(Consumer) z ->System.out.println(z);
        v.accept("Pommes mit Ketchup");
    }
}
