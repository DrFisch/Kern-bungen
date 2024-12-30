package A21;

import java.util.ArrayList;
import java.util.stream.DoubleStream;

public class A21 {
    public static void main(String[] args) {
        var d1 = new double[]{1.0, 2.0, 3.0, 4.0};
        ArrayList<Double> result = DoubleStream.of(d1)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                result.stream().forEach(System.out::println);
    }
}
