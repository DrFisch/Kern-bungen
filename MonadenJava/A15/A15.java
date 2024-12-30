package A15;

import java.util.List;
import java.util.stream.Stream;

public class A15 {
    public static void main(String[] args) {
        List<Integer> l = List.of(1,2,3,4);
        var result1=l.stream().flatMap(x->Stream.of()).toList();
        System.out.println("result1: ");
        result1.forEach(System.out::println);

        // Filtern mit flatMap
        var result2=l.stream().flatMap(x->x%2==0?Stream.of(x):Stream.empty()).toList();
        System.out.println("result 2: ");
        result2.forEach(System.out::println);
    }
}
