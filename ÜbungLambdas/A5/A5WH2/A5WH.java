package A5.A5WH2;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class A5WH {
    public static void main(String[] args) {
        Predicate<Integer> isEven = item -> item%2==0;

        var arr = new Integer[]{1,2,3,4};
        forItemIn(arr, isEven, System.out::println);
    }
    static void forItemIn(Integer[] arr,Predicate<Integer> pred,Consumer<Integer> cons){
        Arrays.stream(arr).filter(pred).forEach(cons);
    }
}
