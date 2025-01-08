package A1.A1WH2;

import java.util.function.Consumer;

interface Drucker {
    void print(String str);
}

public class A1WH2 {
    public static void main(String[] args) {

        //a)
        Drucker druck =x -> System.out.println(x);
        druck = System.out::println;
        druck.print("pimmel");

        //b)
        Consumer cons = System.out::println;
        cons.accept("lol");       

        //c)
        var drucker = (Consumer) System.out::println;
        drucker.accept("try 3");

        //d)
        Consumer<String> consi = x -> System.out.println(x);
        consi.accept("pensi");
        
    }
}
