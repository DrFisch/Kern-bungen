

import java.util.function.Consumer;
import java.util.function.Predicate;

public class A5 {
    public static void main(String[] args) {
        Predicate<Integer> isEven = item -> item % 2 == 0;
        var arr = new Integer[]{1, 2, 3, 4};
forItemIn(arr, isEven, System.out::println);
    }
    public static <T> void forItemIn(T[] array, Predicate<T> predicate, Consumer<T> action) {
        for (T item : array) {
            if (predicate.test(item)) {
                action.accept(item);
            }
        }
    }
}
