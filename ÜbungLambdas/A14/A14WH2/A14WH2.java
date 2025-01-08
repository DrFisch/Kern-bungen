package A14.A14WH2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class A14WH2 {
    public static void main(String[] args) {
        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};
        Function<Double[],Double[]> filterEven= x-> Arrays.stream(x).filter(y->y%2==0).toArray(Double[]::new);
        Function<Double[],Double> sumAll= x -> Arrays.stream(x).reduce(0., Double::sum);
        Function<Double[],Double> sumOfEven=sumAll.compose(filterEven);
        
        Double vectorSum= sumOfEven.apply(v1);
        System.out.println(vectorSum);
    }
}
