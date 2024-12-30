package A5.A5zusammen;

import java.util.function.Predicate;
import java.util.Arrays;
import java.util.function.Consumer;


interface MyPredicate<T>{
    public boolean test(T t);
}

public class A5zusammen {
    public static void main(String[] args) {
        Predicate<Integer> isEven = item -> item % 2 == 0;
        var arr = new Integer[]{1, 2, 3, 4};
        forItemIn(arr, item -> item % 2 == 0, System.out::println); // 2 4
    }
    public static <T> void forItemIn(T[] arr,Predicate<T> pred,Consumer<T> cons){
        Arrays.stream(arr).filter(pred).forEach(cons);
    }
}
