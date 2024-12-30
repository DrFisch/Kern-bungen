package A2.A2Si;

import java.util.function.*;

interface Consumer2<T> {
    void consume(T t);
    
    default void consume(T t, int i){
        for(int j=0;j<i;j++){
            consume(t);
        }
    }
}

public class A2Si {
    public static void main(String[] args) {
        Consumer2 x = System.out::print;
        x.consume("Hallo");
        x.consume("o", 5);
        // Ausgabe: Halloooooo
    }
}
