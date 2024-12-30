package A14.A14Si;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class A14Si {
    public static void main(String[] args) {
        Function<Double[], Double[]> filterEven =v ->{
            List<Double> result=new ArrayList<Double>();
            for (Double d : v) {
                if(d%2==0){
                    result.add(d);
                }
            }
            return result.toArray(Double[]::new);
        };
        Function<Double[], Double> sumAll = v -> {
            return Arrays.stream(v).mapToDouble(Double::doubleValue).sum();
        };

        Double[] v1 = new Double[]{1.0, 2.0, 3.0, 4.0};

        Function<Double[],Double> sumOfEven=sumAll.compose(filterEven);
        Double vectorSum3= sumOfEven.apply(v1);
        
        System.out.println(vectorSum3); // 6.0
    }
}
