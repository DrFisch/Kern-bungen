package A5;

import java.util.function.Predicate;
import java.util.function.Consumer;

public class A5 {
    public static void main(String[] args) {

        Predicate<Integer> isEven = item -> item % 2 == 0;
        var arr = new Integer[]{1, 2, 3, 4};
        forItemIn(arr, isEven, System.out::println); // 2 4
    }
    public static <T> void forItemIn(T[]arr,Predicate<T>gorte,Consumer<T> cons){
        
        for (T zahl : arr) {
            if(gorte.test(zahl)){
                cons.accept(zahl);
            }
        }
    }
}
