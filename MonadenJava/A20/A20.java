package A20;

import java.util.Arrays;
import java.util.stream.Collectors;

public class A20 {
    public static void main(String[] args) {
        int[] arr= {1,3,5,2,8,2,5};
        // a) IntStream
        Arrays.stream(arr).distinct().forEach(System.out::println);
        System.out.println();

        // b) Stream<Integer>
        Arrays.stream(arr).boxed().distinct().forEach(System.out::println);
        System.out.println();

        // c) Collectors.toSet()
        var lol=Arrays.stream(arr).boxed().collect(Collectors.toSet());
        System.out.println(lol);
    }
}
