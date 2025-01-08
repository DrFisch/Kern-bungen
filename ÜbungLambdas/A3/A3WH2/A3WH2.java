package A3.A3WH2;

import java.util.Arrays;

interface Consumer2<T>{
    void consume (T t);
    default void consume(T[] arr){
        Arrays.stream(arr).forEach(this::consume);
    }
}

public class A3WH2 {
    public static void main(String[] args) {
        Consumer2 x = System.out::println;
        x.consume(new String[]{"Alex","Chris","Mike"});
    }
}
