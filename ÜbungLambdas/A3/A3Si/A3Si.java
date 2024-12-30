package A3.A3Si;

import java.util.Arrays;

interface Consumer2<T> {
    void consume(T t);
    
    default void consume(T t, int i){
        for(int j=0;j<i;j++){
            consume(t);
        }
    }
    default void consume(T[] arr){
        consume((T)Arrays.stream(arr).map(x -> x!=arr[arr.length-1]? x+", " : x+"").reduce("", (a,b)->a+b));
    }
}

public class A3Si {
    public static void main(String[] args) {
        Consumer2 x = System.out::println;
        x.consume(new String[]{"Alex", "Chris", "Mike"});
        // Ausgabe: Alex, Chris, Mike
    }
}
