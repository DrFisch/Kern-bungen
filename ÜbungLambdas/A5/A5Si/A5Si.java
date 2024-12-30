package A5.A5Si;

import java.util.Arrays;
import java.util.function.*;

public class A5Si {
    public static void main(String[] args) {
        Predicate<Integer> isEven = item -> item % 2 == 0;
        var arr = new Integer[]{1, 2, 3, 4};
        var arr2 = new Integer[]{1, 2, 3, 4};
        System.out.println(arr2.toString());
        forItemIn(arr, isEven, System.out::println); // 2 4
    }
    public static <T> void forItemIn(T[] arr,Predicate<T> pred,Consumer<String> cons){
        cons.accept(Arrays.toString(Arrays.stream(arr).filter(pred).toArray()));
        //Arrays.stream(arr).filter(pred).forEach(cons);
    }
    // public static <T,R> void forItemIn(T[] arr,Predicate<T> pred,Consumer<R> cons){
    //     //cons.accept(Arrays.stream(arr).filter(pred).toArray().toString());
    //     Arrays.stream(arr).filter(pred).map(x->x+" ").forEach(cons);
    // }
}
